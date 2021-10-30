// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.androidGradle)
        classpath(Plugins.kotlinGradle)
        classpath(Plugins.hiltGradle)
        classpath(Plugins.serializationGradle)
    }

    allprojects {
        configureSpotless()
    }
}
