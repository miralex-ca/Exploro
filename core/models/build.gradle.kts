plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

kotlin {
    android {
        namespace = "com.exploramus.core.models"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        withHostTest { }
    }

    val xcfName = "coreModelsKit"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            binaryOption("bundleId", "com.exploramus.core.models")
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
