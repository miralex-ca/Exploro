import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.skie)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val xcfName = "dataRepositoryKit"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":core:models"))
                implementation(project(":core:common"))
                api(project(":data:common"))


                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                api(libs.multiplatform.settings)
                implementation(libs.koin.core)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.slf4j)
            }
        }

        iosMain {
            dependencies {}
        }
    }
}

android {
    namespace = "com.muralex.data.repository"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}