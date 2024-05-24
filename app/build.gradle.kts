plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kspTool)
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.mapsplatform.secrets)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.gharonkikahani"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gharonkikahani"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11-dev-k2.0.0-Beta4-21f5e479a96"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.coil.compose)
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //material
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)



    //hilt dagger

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)


    ksp(libs.androidx.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    //Type-Safe Compose Navigation

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //splash-screen
    implementation(libs.core.splashscreen)

    //google fonts
    implementation(libs.ui.text.google.fonts)

    implementation(libs.coil.compose)
}



