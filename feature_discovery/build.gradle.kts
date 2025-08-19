apply(from = "../jacoco.gradle.kts")

// https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
plugins {
    alias(libs.plugins.android.library)
    // Apply the `compose.compiler` plugin to every module that uses Jetpack Compose.
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize) // id("kotlin-parcelize")

    alias(libs.plugins.android.junit5)

    alias(libs.plugins.sonarqube)
    jacoco
}

// val jvmTargetVersion by extra {
//     org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(libs.versions.jvmVersion.get())
// }

// val kotlinApi by extra {
//     org.jetbrains.kotlin.gradle.dsl.KotlinVersion.fromVersion(libs.versions.kotlin.api.get())
// }

android {
    namespace = "com.leovp.discovery"

    resourcePrefix = "dis_"

    // https://medium.com/androiddevelopers/5-ways-to-prepare-your-app-build-for-android-studio-flamingo-release-da34616bb946
    buildFeatures {
        // Add this line as needed
        // buildConfig = true
    }
}

// This configuration will override the global setting which is configured in root build.gradle.kts.
// https://kotlinlang.org/docs/gradle-compiler-options.html#target-the-jvm
// tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
//     compilerOptions {
//         // apiVersion.set(kotlinApi)
//         jvmTarget.set(jvmTargetVersion)
//     }
// }

composeCompiler {
    // deprecated
    // enableStrongSkippingMode = true
    // featureFlags.addAll(ComposeFeatureFlag.StrongSkipping, ComposeFeatureFlag.OptimizeNonSkippingGroups)
    includeSourceInformation = true
}

dependencies {
    // hilt - start
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    // hilt - end

    // By using `projects`, you need to enable `enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")`
    // in `settings.gradle.kts` where in your root folder.
    api(projects.featureBase)

    implementation(libs.colorful.slider)
}
