// https://github.com/twilio/twilio-verify-android/blob/main/jacoco.gradle.kts
// https://medium.com/@ranjeetsinha/jacoco-with-kotlin-dsl-f1f067e42cd0
// https://github.com/th-deng/jacoco-on-gradle-sample/blob/master/build.gradle.kts
tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

private val classDirectoriesTree = fileTree(rootProject.layout.buildDirectory) {
    include(
        "**/classes/**/main/**",
        "**/intermediates/classes/debug/**",
        "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
        "**/tmp/kotlin-classes/debug/**"
    )
    exclude(
        // data binding
        "android/databinding/**/*.class",
        "**/android/databinding/*Binding.class",
        "**/android/databinding/*",
        "**/androidx/databinding/*",
        "**/BR.*",
        // android
        "**/R.class",
        "**/R\$*.class",
        "**/*\$1*",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        // kotlin
        "**/*MapperImpl*.*",
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/*Component*.*",
        "**/*BR*.*",
        "**/*\$Lambda\$*.*", // Jacoco can not handle several "$" in class name.
        "**/*\$inlined\$*.*", // Kotlin specific, Jacoco can not handle several "$" in class name.
        "**/*Companion*.*",
        "**/*Module.*", /* filtering Dagger modules classes */
        "**/*Dagger*.*", /* filtering Dagger-generated classes */
        "**/*Hilt*.*",
        "**/*MembersInjector*.*",
        "**/*_MembersInjector.class",
        "**/*_Factory*.*",
        "**/*_Provide*Factory*.*",
        "**/*Extensions*.*",
        // sealed and data classes
        "**/*\$Result.*",
        "**/*\$Result\$*.*",
        // Navigation Component generated classes
        "**/*Args*.*",
        "**/*Directions*.*",
        // adapters generated by moshi
        "**/*JsonAdapter.*"
    )
}

private val sourceDirectoriesTree = fileTree(rootProject.layout.buildDirectory) {
    include(
        "src/main/java/**",
        "src/main/kotlin/**",
        "src/debug/java/**",
        "src/debug/kotlin/**"
    )
}

private val executionDataTree = fileTree(rootProject.layout.buildDirectory) {
    include(
        "outputs/code_coverage/**/*.ec",
        "jacoco/jacocoTestReportDebug.exec",
        "jacoco/testDebugUnitTest.exec",
        "jacoco/test.exec"
    )
}

fun JacocoReportsContainer.reports() {
    csv.required.set(false)
    xml.apply {
        required.set(true)
        outputLocation.set(file("${rootProject.layout.buildDirectory}/reports/code-coverage/xml"))
    }
    html.apply {
        required.set(true)
        outputLocation.set(file("${rootProject.layout.buildDirectory}/reports/code-coverage/html"))
    }
}

fun JacocoReport.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

fun JacocoCoverageVerification.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

val jacocoGroup = "verification"
tasks.register<JacocoReport>("jacocoTestReport") {
    group = jacocoGroup
    description = "Code coverage report for both Android and Unit tests."
    dependsOn("testDebugUnitTest")
    reports {
        reports()
    }
    setDirectories()
}

tasks.register<JacocoCoverageVerification>("jacocoCoverageVerification") {
    group = jacocoGroup
    description = "Code coverage verification for Android both Android and Unit tests."
    dependsOn("testDebugUnitTest")
    violationRules {
        rule {
            limit {
//                counter = "INSTRUCTIONAL"
                value = "COVEREDRATIO"
                minimum = "0.3".toBigDecimal()
            }
        }
        rule {
            enabled = true

            element = "CLASS"
            excludes = listOf(
                "**.FactorFacade.Builder",
                "**.ServiceFacade.Builder",
                "**.ChallengeFacade.Builder",
                "**.Task"
            )
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.4".toBigDecimal()
            }
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }
        }
    }
    setDirectories()
}
