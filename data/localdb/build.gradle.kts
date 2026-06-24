plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget()

    val xcfName = "dataLocaldbKit"

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
                implementation(project(":core:models"))
                api(project(":data:common"))
                implementation(libs.sqldelight.common)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.sqldelight.android)
                implementation(libs.koin.android)
            }
        }

        androidUnitTest {
            dependencies {
                implementation(libs.sqldelight.jvm)
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
    namespace = "com.exploramus.data.localdb"
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