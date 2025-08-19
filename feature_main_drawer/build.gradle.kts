import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.composeCompiler
import org.gradle.kotlin.dsl.projects

apply(from = "../jacoco.gradle.kts")

// https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
plugins {
    alias(libs.plugins.android.library)
    // Apply the `compose.compiler` plugin to every module that uses Jetpack Compose.
    alias(libs.plugins.kotlin.compose.compiler)
    // alias(libs.plugins.com.android.dynamic.feature)
    alias(libs.plugins.kotlin.parcelize) // id("kotlin-parcelize")

    // Add ksp only if you use ksp() in dependencies {}
    // alias(libs.plugins.ksp)

    alias(libs.plugins.android.junit5)

    alias(libs.plugins.sonarqube)
    jacoco
}

android {
    namespace = "com.leovp.maindrawer"

    resourcePrefix = "drawer_"

    // https://medium.com/androiddevelopers/5-ways-to-prepare-your-app-build-for-android-studio-flamingo-release-da34616bb946
    buildFeatures {
    }
}

composeCompiler {
    // deprecated
    // enableStrongSkippingMode = true
    // featureFlags.addAll(ComposeFeatureFlag.StrongSkipping, ComposeFeatureFlag.OptimizeNonSkippingGroups)
    includeSourceInformation = true
}

dependencies {
    // By using `projects`, you need to enable `enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")`
    // in `settings.gradle.kts` where in your root folder.
    api(projects.featureBase)
}
