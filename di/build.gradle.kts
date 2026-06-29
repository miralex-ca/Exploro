plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

kotlin {
    android {
        namespace = "com.exploramus.di"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        withHostTest { }
    }

    val xcfName = "diKit"

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
