buildscript{
    dependencies{
        classpath("com.google.gms:google-services:4.4.2")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.google.gms.google.services) apply false
  //  alias("com.google.gms.google-services") version "4.4.2" apply false

}

    // ...

    // Add the dependency for the Google services Gradle plugin

