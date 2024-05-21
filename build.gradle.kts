// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.kspTool) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
    alias(libs.plugins.mapsplatform.secrets) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}