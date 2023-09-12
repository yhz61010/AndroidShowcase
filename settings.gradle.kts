rootProject.name = "AndroidShowcase"

// https://developer.android.com/studio/build?hl=zh-cn#settings-file
pluginManagement {
    /**
     * The pluginManagement {repositories {...}} block configures the
     * repositories Gradle uses to search or download the Gradle plugins and
     * their transitive dependencies. Gradle pre-configures support for remote
     * repositories such as JCenter, Maven Central, and Ivy. You can also use
     * local repositories or define your own remote repositories. The code below
     * defines the Gradle Plugin Portal, Google's Maven repository,
     * and the Maven Central Repository as the repositories Gradle should use to
     * look for its dependencies.
     */
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    /**
     * The dependencyResolutionManagement {repositories {...}}
     * block is where you configure the repositories and dependencies used by
     * all modules in your project, such as libraries that you are using to
     * create your application. However, you should configure module-specific
     * dependencies in each module-level build.gradle file. For new projects,
     * Android Studio includes Google's Maven repository and the
     * Maven Central Repository by default,
     * but it does not configure any dependencies (unless you select a
     * template that requires some).
     */
    @Suppress ("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress ("UnstableApiUsage")
    repositories {
        // https://github.com/airbnb/lottie/blob/master/android-compose.md
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }

        google()
        mavenCentral {
            isAllowInsecureProtocol = true
        }
        maven("https://maven.aliyun.com/repository/public")
        maven("https://jitpack.io")
    }
}

// https://docs.gradle.org/7.0/release-notes.html
// Type-safe project accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":app",
    ":feature_base",
    ":feature_discovery",
    ":feature_my",
    ":feature_community",
    ":feature_main_drawer"
)
