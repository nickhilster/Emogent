package app.emogent.keyboard

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class SetupActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val padding = (20 * resources.displayMetrics.density).toInt()
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(padding, padding, padding, padding)
        }
        layout.addView(TextView(this).apply {
            text = getString(R.string.setup_title)
            textSize = 24f
        })
        layout.addView(TextView(this).apply {
            text = getString(R.string.setup_copy)
            textSize = 16f
            setPadding(0, padding / 2, 0, padding)
        })
        layout.addView(Button(this).apply {
            text = getString(R.string.enable_keyboard)
            setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) }
        })
        layout.addView(Button(this).apply {
            text = getString(R.string.choose_keyboard)
            setOnClickListener {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .showInputMethodPicker()
            }
        })
        layout.addView(TextView(this).apply {
            text = getString(R.string.test_keyboard)
            textSize = 18f
            setPadding(0, padding, 0, 0)
        })
        layout.addView(EditText(this).apply {
            hint = getString(R.string.test_hint)
            inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES
            minLines = 2
        }, LinearLayout.LayoutParams(-1, -2))
        setContentView(layout)
    }
}
