object Plugins {
    const val androidGradle = "com.android.tools.build:gradle:7.0.3"
    const val kotlinGradle =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.kotlinVersion}"
    const val hiltGradle =
        "com.google.dagger:hilt-android-gradle-plugin:${Dependencies.Dagger.version}"
    const val serializationGradle =
        "org.jetbrains.kotlin:kotlin-serialization:${Dependencies.kotlinVersion}"

    object BuildPlugins {
        const val application = "com.android.application"
        const val kotlinAndroid = "android"
        const val kotlinSerialization = "plugin.serialization"
        const val kotlinKapt = "kapt"
        const val hilt = "dagger.hilt.android.plugin"
        const val spotless = "com.diffplug.spotless"
    }
}
