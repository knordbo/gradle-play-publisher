import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

buildscript {
    repositories.deps()

    dependencies {
        classpath(kotlin("gradle-plugin", embeddedKotlinVersion))
    }
}

plugins {
    `build-scan`
    id("com.github.triplet.gradle.build")
    id("com.github.ben-manes.versions") version "0.25.0"
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

tasks.register<Delete>("clean") {
    delete("build", "testapp/build")
}

allprojects {
    repositories.deps()

    afterEvaluate {
        convention.findByType<KotlinProjectExtension>()?.apply {
            sourceSets.configureEach {
                languageSettings.progressiveMode = true
                languageSettings.enableLanguageFeature("NewInference")
                languageSettings.useExperimentalAnnotation(
                        "kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    if (System.getenv("CI") != null) {
        tasks.withType<Test> {
            testLogging {
                events("passed", "failed", "skipped")
                showStandardStreams = true
            }
        }
    }
}

apply(from = "testapp-setup.gradle.kts")
