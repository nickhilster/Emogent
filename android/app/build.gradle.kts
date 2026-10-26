plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "app.emogent.keyboard"
    compileSdk = 35

    defaultConfig {
        applicationId = "app.emogent.keyboard"
        minSdk = 23
        targetSdk = 28
        versionCode = 1
        versionName = "0.1.0"
    }
}
