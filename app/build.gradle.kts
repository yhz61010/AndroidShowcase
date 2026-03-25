import com.android.build.api.variant.BuildConfigField
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties

// https://developer.android.com/studio/build?hl=zh-cn#module-level

apply(from = "../jacoco.gradle.kts")
// https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
plugins {
    alias(libs.plugins.android.application)
    // Apply the `compose.compiler` plugin to every module that uses Jetpack Compose.
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize) // id("kotlin-parcelize")

    // https://github.com/mannodermaus/android-junit5
    alias(libs.plugins.android.junit5)

    alias(libs.plugins.sonarqube)
    jacoco
}

junitPlatform {
    jacocoOptions {
        taskGenerationEnabled.set(false)
    }
}

// val jvmTargetVersion by extra {
//     org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(libs.versions.jvmVersion.get())
// }

// val kotlinApi by extra {
//     org.jetbrains.kotlin.gradle.dsl.KotlinVersion.fromVersion(libs.versions.kotlin.api.get())
// }

android {
    /** The app's namespace. Used primarily to access app resources. */
    namespace = "com.leovp.androidshowcase"

    resourcePrefix = "app_"

    /** Specifies one flavor dimension. */
    flavorDimensions += listOf("version")

    defaultConfig {
        applicationId = namespace

        versionCode =
            libs.versions.versionCode
                .get()
                .toInt()
        versionName = libs.versions.versionName.get()

        ndk {
            // abiFilters "arm64-v8a", "armeabi-v7a", "x86", "x86_64"
            // noinspection ChromeOsAbiSupport
            abiFilters += setOf("armeabi-v7a")
        }

        // https://github.com/mannodermaus/android-junit5
        // Connect JUnit 5 to the runner
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    // https://medium.com/androiddevelopers/5-ways-to-prepare-your-app-build-for-android-studio-flamingo-release-da34616bb946
    buildFeatures {
        // dataBinding = true
        // aidl = true

        // viewBinding is enabled by default. Check [build.gradle.kts] in the root folder of project.
        // viewBinding = true

        // Generate BuildConfig.java file
        buildConfig = true
    }

    signingConfigs {
        named("debug") {
            keyAlias = getSignProperty("keyAlias")
            keyPassword = getSignProperty("keyPassword")
            storeFile = File(rootDir, getSignProperty("storeFile"))
            storePassword = getSignProperty("storePassword")
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }

        create("release") {
            keyAlias = getSignProperty("keyAlias")
            keyPassword = getSignProperty("keyPassword")
            storeFile = File(rootDir, getSignProperty("storeFile"))
            storePassword = getSignProperty("storePassword")
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }

    /**
     * The productFlavors block is where you can configure multiple product flavors.
     * This allows you to create different versions of your app that can
     * override the defaultConfig block with their own settings. Product flavors
     * are optional, and the build system does not create them by default.
     *
     * This example creates a free and paid product flavor. Each product flavor
     * then specifies its own application ID, so that they can exist on the Google
     * Play Store, or an Android device, simultaneously.
     *
     * If you declare product flavors, you must also declare flavor dimensions
     * and assign each flavor to a flavor dimension.
     */
    productFlavors {
        create("dev") {
            // Assigns this product flavor to the "version" flavor dimension.
            // If you are using only one dimension, this property is optional,
            // and the plugin automatically assigns all the module's flavors to
            // that dimension.
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("prod") {
            dimension = "version"
        }
    }

    buildTypes {
        debug {
            // getByName("debug")
            signingConfig = signingConfigs.getByName("debug")
        }

        /**
         * By default, Android Studio configures the release build type to enable code
         * shrinking, using minifyEnabled, and specifies the default Proguard rules file.
         *
         * See the global configurations in top-level `build.gradle.kts`.
         */
        release {
            // getByName("release")
            signingConfig = signingConfigs.getByName("release")
        }

        /**
         * The `initWith` property allows you to copy configurations from other build types,
         * then configure only the settings you want to change. This one copies the debug build
         * type, and then changes the manifest placeholder and application ID.
         */
        // Uncommented the following block will cause build exception
        // if using ./gradlew build command
        // create("staging") {
        //     initWith(getByName("debug"))
        //     // https://developer.android.com/studio/build/manifest-build-variables
        //     // manifestPlaceholders["hostName"] = "internal.example.com"
        //     // applicationIdSuffix = ".staging"
        // }
    }

    // https://developer.android.com/reference/tools/gradle-api/7.1/com/android/build/api/dsl/Lint
    lint {
        // if true, stop the Gradle build if errors are found
        abortOnError = false
        // Like checkTestSources, but always skips analyzing tests -- meaning that it
        // also ignores checks that have explicitly asked to look at test sources, such
        // as the unused resource check.
        ignoreTestSources = true
    }

}

androidComponents {
    onVariants { variant ->
        val isConsoleLogOpen = variant.name != "prodRelease"
        variant.buildConfigFields?.put(
            "CONSOLE_LOG_OPEN",
            BuildConfigField("boolean", isConsoleLogOpen.toString(), null),
        )

        // Rename APK output files
        val appName = "LeoAndroidShowcase"
        // Example: dev, prod
        val flavorName = variant.flavorName.orEmpty()
        // Example: debug, release
        val buildTypeName = variant.buildType.orEmpty()
        // Example: DevDebug, ProdRelease
        val capitalizedName = variant.name.replaceFirstChar { it.uppercase() }
        val mainOutput = variant.outputs.firstOrNull()
        val versionName = mainOutput?.versionName?.getOrElse("") ?: ""
        val versionCode = mainOutput?.versionCode?.getOrElse(0) ?: 0
        // val versionName = android.defaultConfig.versionName ?: "NA"
        // val versionCode = android.defaultConfig.versionCode ?: 0
        // Example: 20260325_104413(CST)
        val timestamp = ZonedDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss(z)"))
        // Example: 18cc7c4
        val gitTag = gitVersionTag()
        // Example: 148
        val gitCount = gitCommitCount()
        println("buildTypeName=$buildTypeName flavorName=$flavorName capitalizedName=$capitalizedName gitTag=$gitTag gitCount=$gitCount")
        // Example: LeoAndroidShowcase-dev-debug-v1.0.5-dev(105)-20260325_104413(CST)-18cc7c4-148.apk
        val apkName =
            "$appName${("-$flavorName").takeIf {
                it != "-"
            } ?: ""}-$buildTypeName" +
                "-v$versionName($versionCode)" +
                "-$timestamp" +
                "-$gitTag-$gitCount" +
                ".apk"

        tasks.register("rename${capitalizedName}Apk") {
            val apkDir = variant.artifacts.get(com.android.build.api.artifact.SingleArtifact.APK)
            inputs.dir(apkDir)
            doLast {
                val dir = apkDir.get().asFile
                dir.listFiles()?.filter { it.extension == "apk" }?.forEach { srcFile ->
                    val finalName = if ("unsigned" in srcFile.name) {
                        apkName.replace(".apk", "-unsigned.apk")
                    } else {
                        apkName
                    }
                    srcFile.copyTo(File(dir, finalName), overwrite = true)
                }
            }
        }
        afterEvaluate {
            tasks.named("assemble${capitalizedName}") {
                finalizedBy("rename${capitalizedName}Apk")
            }
        }
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

/** Note that, the composeCompiler is outside the android node. */
// composeCompiler {
//     enableStrongSkippingMode = true
//
//     reportsDestination = layout.buildDirectory.dir("compose_compiler")
//     stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
// }

composeCompiler {
    // deprecated
    // enableStrongSkippingMode = true
    // featureFlags.addAll(ComposeFeatureFlag.StrongSkipping, ComposeFeatureFlag.OptimizeNonSkippingGroups)
    includeSourceInformation = true
}

// 获取当前分支的提交总次数
fun gitCommitCount(): String {
//    val cmd = 'git rev-list HEAD --first-parent --count'
    val cmd = "git rev-list HEAD --count"

    return runCatching {
        // You must trim() the result. Because the result of command has a suffix '\n'.
        providers
            .exec {
                commandLine = cmd.trim().split(' ')
            }.standardOutput.asText
            .get()
            .trim()
    }.getOrDefault("NA")
}

// 使用commit的哈希值作为版本号也是可以的，获取最新的一次提交的哈希值的前七个字符
// $ git rev-list HEAD --abbrev-commit --max-count=1
// a935b078

/*
 * 获取最新的一个tag信息
 * $ git describe --tags
 * 4.0.4-9-ga935b078
 * 说明：
 * 4.0.4        : tag名
 * 9            : 打tag之后又有四次提交
 * ga935b078    ：开头 g 为 git 的缩写，在多种管理工具并存的环境中很有用处
 * a935b078     ：当前分支最新的 commitID 前几位
 */
fun gitVersionTag(): String {
    // https://stackoverflow.com/a/4916591/1685062
//    val cmd = "git describe --tags"
    val cmd = "git describe --always"

    val versionTag =
        runCatching {
            // You must trim() the result. Because the result of command has a suffix '\n'.
            providers
                .exec {
                    commandLine = cmd.trim().split(' ')
                }.standardOutput.asText
                .get()
                .trim()
        }.getOrDefault("NA")

    val regex = "-(\\d+)-g".toRegex()
    val matcher: MatchResult? = regex.matchEntire(versionTag)

    val matcherGroup0: MatchGroup? = matcher?.groups?.get(0)
    return if (matcher?.value?.isNotBlank() == true &&
        matcherGroup0?.value?.isNotBlank() == true
    ) {
        versionTag.substring(0, matcherGroup0.range.first) + "." +
            matcherGroup0.value
    } else {
        versionTag
    }
}

fun Project.getSignProperty(
    key: String,
    path: String = "config/sign/keystore.properties",
): String =
    Properties()
        .apply {
            rootProject.file(path).inputStream().use(::load)
        }.getProperty(key)

dependencies {
    // hilt - start
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    // hilt - end

    // By using `projects`, you need to enable `enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")`
    // in `settings.gradle.kts` where in your root folder.
    implementation(projects.featureDiscovery)
    implementation(projects.featureMy)
    implementation(projects.featureCommunity)
    implementation(projects.featureMainDrawer)

    // ==============================
    implementation(libs.compose.runtime.tracing)
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
