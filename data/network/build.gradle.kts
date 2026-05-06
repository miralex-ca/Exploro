plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {

    androidLibrary {
        namespace = "com.muralex.data.network"
        compileSdk = 36
        minSdk = 24

    }

    val xcfName = "dataNetworkKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:models"))
                implementation(project(":data:common"))
                implementation(libs.ktor.core)
                implementation(libs.ktor.contentNegotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.kotlinx.serialization.json)

            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.ios)
            }
        }
    }

}