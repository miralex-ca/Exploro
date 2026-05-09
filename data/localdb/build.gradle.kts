plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
   // alias(libs.plugins.androidLint)
    alias(libs.plugins.sqlDelight)
}

kotlin {
//    androidLibrary {
//        namespace = "com.muralex.data.localdb"
//        compileSdk = 36
//        minSdk = 24
//    }

    androidTarget()

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
                api(project(":data:common"))
                implementation(libs.sqldelight.common)
                implementation(libs.koin.core)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.sqldelight.android)
                implementation(libs.koin.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.sqldelight.ios)
            }
        }
    }
}

android {
    namespace = "com.muralex.data.localdb"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}

sqldelight {
    databases {
        create("AppLocalDb") {
            packageName.set("appLocalDb")
        }
    }
}