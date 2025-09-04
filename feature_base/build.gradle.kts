@file:Suppress(
    "ktlint:standard:max-line-length", // for ktlint
    "MaximumLineLength", // for detekt
    "MaxLineLength", // for detekt
    "LongLine", // for detekt
)

import com.android.build.api.dsl.LibraryDefaultConfig
import java.util.Locale

apply(from = "../jacoco.gradle.kts")

// https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
plugins {
    alias(libs.plugins.android.library)
    // Apply the `compose.compiler` plugin to every module that uses Jetpack Compose.
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.kotlin.parcelize) // id("kotlin-parcelize")

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)

    alias(libs.plugins.android.junit5)

    alias(libs.plugins.sonarqube)
    jacoco
}

android {
    namespace = "com.leovp.feature.base"

    resourcePrefix = "bas_"

    // https://medium.com/androiddevelopers/5-ways-to-prepare-your-app-build-for-android-studio-flamingo-release-da34616bb946
    buildFeatures {
        // dataBinding = true
        // viewBinding is enabled by default. Check [build.gradle.kts] in the root folder of project.
        // viewBinding = true
        // aidl = true

        // Add this line as needed
        buildConfig = true
    }

    defaultConfig {
        // Connect JUnit 5 to the runner
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"

        buildConfigFieldFromGradleProperty("apiBaseUrl")
        // buildConfigFieldFromGradleProperty("apiToken")

        buildConfigField(
            "String",
            "VERSION_NAME",
            "\"${libs.versions.versionName.get()}\"",
        )
    }

    // https://developer.android.com/reference/tools/gradle-api/7.1/com/android/build/api/dsl/Lint
    lint {
        // if true, stop the gradle build if errors are found
        abortOnError = false
        // Like checkTestSources, but always skips analyzing tests -- meaning that it
        // also ignores checks that have explicitly asked to look at test sources, such
        // as the unused resource check.
        ignoreTestSources = true
    }

    buildTypes {
        debug {
            // getByName("debug")
            buildConfigField("boolean", "DEBUG_MODE", "true")
        }

        release {
            // getByName("release")
            buildConfigField("boolean", "DEBUG_MODE", "false")
        }
    }
}

ksp {
    // The following two lines will suppress the warning:
    // Hilt_CustomApplication.java:25: 警告: [deprecation] Builder中的applicationContextModule(ApplicationContextModule)已过时
    //           .applicationContextModule(new ApplicationContextModule(Hilt_CustomApplication.this))
    arg("dagger.fastInit", "enabled")
    arg("dagger.formatGeneratedSource", "disabled")
}

composeCompiler {
    // deprecated
    // enableStrongSkippingMode = true
    // featureFlags.addAll(ComposeFeatureFlag.StrongSkipping, ComposeFeatureFlag.OptimizeNonSkippingGroups)
    includeSourceInformation = true
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    // Material Design 3
    api(libs.androidx.material3)
    api(libs.bundles.androidx.compose)
    // Android Studio Preview support
    api(libs.androidx.compose.ui.tooling.preview)
    // api(libs.androidx.compose.ui.graphics)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    api(libs.androidx.navigation.compose)
    // ----------

    // hilt - start
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    // hilt - end

    api(libs.bundles.kotlin)
    api(libs.androidx.lifecycle.runtime.compose)

    api(libs.leo.androidbase)
    api(libs.leo.lib.compose)
    api(libs.leo.lib.network)
    // api(libs.leo.log)
    // api(libs.leo.lib.json)
    // api(libs.leo.lib.common.kotlin)
    api(libs.leo.pref)
    // implementation(libs.leo.floatview)

    // Net - dependencies - Start
    api(libs.kotlin.coroutines)
    api(libs.square.okhttp)
    api(libs.net)
    // Net - dependencies - End

    api(libs.serialization.json)
    api(libs.mars.xlog)
    api(libs.bundles.coil)
    api(libs.karn.notify)
    // api(libs.lottie.compose)

    // ==============================
    testImplementation(libs.bundles.test)
    // testRuntimeOnly(libs.bundles.test.runtime.only)
    // androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // ==============================
    // The instrumentation test companion libraries
    // https://github.com/mannodermaus/android-junit5
    // ==============================
    androidTestImplementation(libs.mannodermaus.junit5.core)
    androidTestRuntimeOnly(libs.mannodermaus.junit5.runner)
    // ==============================
}

/*
 * Takes value from Gradle project property and sets it as Android build config property
 * eg. apiToken variable present in the gradle.properties file
 * will be accessible as BuildConfig.GRADLE_API_TOKEN in the app.
 */
fun LibraryDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(
        propertyValue,
    ) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName =
        "GRADLE_${gradlePropertyName.toSnakeCase()}".uppercase(
            Locale.getDefault(),
        )
    buildConfigField("String", androidResourceName, propertyValue)
}

fun String.toSnakeCase() =
    this.split(Regex("(?=[A-Z])")).joinToString("_") {
        it.lowercase(Locale.getDefault())
    }
