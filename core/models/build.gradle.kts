plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()

    val xcfName = "coreModelsKit"

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
            dependencies { }
        }

        androidMain {
            dependencies { }
        }

        iosMain {
            dependencies { }
        }
    }
}

android {
    namespace = "com.exploramus.core.models"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
}
