plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidLibrary {
        namespace = "com.muralex.data.localdb"
        compileSdk = 36
        minSdk = 24
    }

    val xcfName = "dataLocaldbKit"

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
                implementation(libs.sqldelight.common)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.sqldelight.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.sqldelight.ios)
            }
        }
    }
}

sqldelight {
    databases {
        create("AppLocalDb") {
            packageName.set("appLocalDb")
        }
    }
}