package app.emogent.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout

class EmogentImeService : InputMethodService() {
    private var shifted = false
    private var capsLock = false
    private var symbols = false
    private var secureField = false

    override fun onCreateInputView(): View = buildKeyboard()

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        shifted = info?.let { it.inputType and EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES != 0 } ?: false
        capsLock = false
        secureField = info?.let {
            val inputClass = it.inputType and EditorInfo.TYPE_MASK_CLASS
            val variation = it.inputType and EditorInfo.TYPE_MASK_VARIATION
            (inputClass == EditorInfo.TYPE_CLASS_NUMBER && variation == EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD) ||
                (inputClass == EditorInfo.TYPE_CLASS_TEXT && variation in setOf(
                    EditorInfo.TYPE_TEXT_VARIATION_PASSWORD,
                    EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
                    EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD,
                ))
        } ?: false
        if (secureField) {
            window?.window?.setFlags(
                android.view.WindowManager.LayoutParams.FLAG_SECURE,
                android.view.WindowManager.LayoutParams.FLAG_SECURE,
            )
        } else {
            window?.window?.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
        }
        symbols = when (info?.inputType?.and(EditorInfo.TYPE_MASK_CLASS)) {
            EditorInfo.TYPE_CLASS_NUMBER, EditorInfo.TYPE_CLASS_PHONE -> true
            else -> false
        }
        setInputView(buildKeyboard())
    }

    private fun buildKeyboard(): View {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(3), dp(4), dp(3), dp(4))
            setBackgroundColor(0xFFE1E5EA.toInt())
        }
        if (symbols) {
            listOf("1234567890", "@#$%&*-+()", "!\"':;/?") .forEach { row ->
                addRow(root, row.map { it.toString() })
            }
        } else {
            addRow(root, "qwertyuiop".map { letter(it) })
            addRow(root, "asdfghjkl".map { letter(it) })
            addRow(root, listOf("⇧") + "zxcvbnm".map { letter(it) } + "⌫")
        }
        val bottom = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        addKey(bottom, if (symbols) "ABC" else "?123", 1.25f) {
            symbols = !symbols
            setInputView(buildKeyboard())
        }
        addKey(bottom, ",", 0.7f) { commit(",") }
        addKey(bottom, "EN", 0.65f) { switchToNextInputMethod(false) }
        addKey(bottom, "space", 4.4f) { commit(" "); shifted = false; refresh() }
        if (!secureField) addKey(bottom, if (isEmailOrUri()) "." else ".", 0.8f) { commit(".") }
        addKey(bottom, actionLabel(), 1.3f) { performAction() }
        root.addView(bottom, rowParams())
        return root
    }

    private fun addRow(root: LinearLayout, labels: List<String>) {
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        labels.forEach { label ->
            addKey(row, label, 1f) {
                when (label) {
                    "⇧" -> { if (shifted) capsLock = !capsLock else shifted = true; refresh() }
                    "⌫" -> deleteBeforeCursor()
                    else -> { commit(if (shifted || capsLock) label.uppercase() else label); if (!capsLock) shifted = false; refresh() }
                }
            }
        }
        root.addView(row, rowParams())
    }

    private fun addKey(row: LinearLayout, label: String, weight: Float, action: () -> Unit) {
        val key = Button(this).apply {
            text = label
            textSize = if (label.length > 2) 13f else 18f
            isAllCaps = false
            setPadding(0, 0, 0, 0)
            minHeight = dp(44)
            setOnClickListener { action() }
        }
        row.addView(key, LinearLayout.LayoutParams(0, dp(48), weight).apply {
            setMargins(dp(2), dp(2), dp(2), dp(2))
        })
    }

    private fun rowParams() = LinearLayout.LayoutParams(-1, -2)
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
    private fun letter(value: Char) = if (shifted || capsLock) value.uppercaseChar().toString() else value.toString()
    private fun commit(value: String) { currentInputConnection?.commitText(value, 1) }

    private fun deleteBeforeCursor() {
        val connection = currentInputConnection ?: return
        if (!connection.deleteSurroundingTextInCodePoints(1, 0)) {
            connection.deleteSurroundingText(1, 0)
        }
    }

    private fun performAction() {
        val connection = currentInputConnection ?: return
        val action = currentInputEditorInfo?.imeOptions?.and(EditorInfo.IME_MASK_ACTION)
        when (action) {
            EditorInfo.IME_ACTION_GO, EditorInfo.IME_ACTION_SEARCH,
            EditorInfo.IME_ACTION_SEND, EditorInfo.IME_ACTION_DONE -> connection.performEditorAction(action)
            EditorInfo.IME_ACTION_NEXT -> connection.performEditorAction(EditorInfo.IME_ACTION_NEXT)
            else -> {
                if (currentInputEditorInfo?.inputType?.and(EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE) != 0) commit("\n")
                else connection.performEditorAction(EditorInfo.IME_ACTION_UNSPECIFIED)
            }
        }
    }

    private fun actionLabel(): String = when (currentInputEditorInfo?.imeOptions?.and(EditorInfo.IME_MASK_ACTION)) {
        EditorInfo.IME_ACTION_GO -> "Go"
        EditorInfo.IME_ACTION_SEARCH -> "Search"
        EditorInfo.IME_ACTION_SEND -> "Send"
        EditorInfo.IME_ACTION_NEXT -> "Next"
        else -> "Done"
    }

    private fun isEmailOrUri(): Boolean = when (currentInputEditorInfo?.inputType?.and(EditorInfo.TYPE_MASK_VARIATION)) {
        EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, EditorInfo.TYPE_TEXT_VARIATION_URI,
        EditorInfo.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS -> true
        else -> false
    }

    private fun refresh() { setInputView(buildKeyboard()) }
}
