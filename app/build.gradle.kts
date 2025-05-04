plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.drineczki"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.drineczki"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.window.size.class1.android)
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation(libs.androidx.media3.common.ktx)
    val roomVersion = "2.6.1"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation(libs.androidx.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2023.09.01")) // BOM dla Compose
    implementation("androidx.compose.ui:ui:1.5.0") // Podstawowe funkcje Compose
    implementation("androidx.compose.ui:ui-graphics:1.5.0") // Funkcje graficzne
    implementation("androidx.compose.ui:ui-tooling:1.5.0") // Narzędzia Compose
    implementation("androidx.compose.animation:animation:1.5.0") // Animacje (opcjonalne)
    implementation(libs.androidx.material3)
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("com.google.accompanist:accompanist-pager:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}