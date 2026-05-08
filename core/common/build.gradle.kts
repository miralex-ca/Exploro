plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {

    androidTarget()

    val xcfName = "coreCommonKit"

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
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        androidMain {
            dependencies {
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.muralex.core.common"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}