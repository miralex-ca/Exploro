import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val xcfName = "dataAssetsKit"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:models"))
                implementation(project(":core:common"))
                implementation(project(":data:common"))

                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        iosMain {
            dependencies {}
        }
    }
}

android {
    namespace = "com.exploramus.data.assets"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Sync assets to platform targets
val syncAssetsToAndroid by tasks.registering(Copy::class) {
    from("src/commonMain/resources/")
    into("${rootProject.projectDir}/composeApp/src/main/assets/")
}

val syncAssetsToIos by tasks.registering(Copy::class) {
    from("src/commonMain/resources/")
    into("${rootProject.projectDir}/iosApp/iosApp/AppRawData/")
}

tasks.named("preBuild") {
    dependsOn(syncAssetsToAndroid, syncAssetsToIos)
}