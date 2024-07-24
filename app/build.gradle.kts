plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    //alias(libs.plugins.google.gms.google.services) // this version matches your Kotlin version
    id("com.google.gms.google-services") version "4.4.2" apply true

}

android {
    namespace = "com.example.mykotlinproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mykotlinproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }


}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.junit)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.compiler)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)


    implementation("androidx.compose.material3:material3")

    implementation("androidx.compose.ui:ui")

    // Android Studio Preview support
    //noinspection UseTomlInstead
    implementation("androidx.compose.ui:ui-tooling-preview")
    //noinspection UseTomlInstead
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    //noinspection UseTomlInstead
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //noinspection UseTomlInstead
    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    //noinspection UseTomlInstead
    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    //noinspection UseTomlInstead
    implementation("androidx.compose.material3:material3-window-size-class")

    //noinspection UseTomlInstead
    implementation("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with RxJava
    //noinspection UseTomlInstead
    implementation("androidx.compose.runtime:runtime-rxjava2")
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.arrow.core)
    implementation(libs.coil.compose)
    implementation(libs.androidx.hilt.navigation.fragment)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.jetbrains.kotlinx.coroutines.core)

    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose.v170)
    implementation(libs.maps.compose)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.jetbrains.kotlinx.coroutines.core)

    implementation(libs.volley)
    implementation(libs.jetbrains.kotlin.stdlib)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:")
    implementation(libs.gson)


    implementation("com.google.guava:guava:32.1.2-jre")

    //firebase DB
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation(libs.firebase.analytics.ktx)


    //tsets
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    // For coroutine testing
    testImplementation(libs.kotlinx.coroutines.test.v152)
    testImplementation(libs.androidx.core.testing)
    testImplementation("org.robolectric:robolectric:4.8")

    implementation("org.jsoup:jsoup:1.14.3")

}
