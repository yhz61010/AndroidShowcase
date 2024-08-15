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
        // 阿里云的 Gradle 插件镜像
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        // 腾讯云的 Gradle 插件镜像
        maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/gradle-plugins/") }
        // Google 官方插件镜像（通过阿里云镜像）
        maven { url = uri("https://maven.aliyun.com/repository/google") }

        // 如果有需要，可以保留 Gradle 默认的插件门户
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
        google()
        mavenCentral {
            isAllowInsecureProtocol = true
        }
        maven("https://jitpack.io")

        // 腾讯云/阿里云 maven 镜像聚合了：central、jcenter、google、gradle-plugin
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/central")

        maven("https://mirrors.cloud.tencent.com/gradle/")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")

        maven("https://plugins.gradle.org/m2/")
        maven("https://maven.java.net/content/groups/public/")
        // https://github.com/airbnb/lottie/blob/master/android-compose.md
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
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
