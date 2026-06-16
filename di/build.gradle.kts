plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {

    androidTarget()

    val xcfName = "diKit"

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
                implementation(project(":core:common"))
                implementation(project(":data:repository"))
                implementation(project(":data:localdb"))
                implementation(project(":data:network"))
                implementation(project(":data:assets"))
                implementation(libs.koin.core)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
            }
        }


        iosMain {
            dependencies {
            }
        }
    }

}

android {
    namespace = "com.muralex.di"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}