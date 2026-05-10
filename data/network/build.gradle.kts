import java.util.Properties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildKonfig)
}

kotlin {
    androidTarget()

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
                api(project(":core:models"))
                api(project(":data:common"))
                implementation(libs.ktor.core)
                implementation(libs.ktor.contentNegotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.koin.core)
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

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}

android {
    namespace = "com.muralex.data.network"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
}

buildkonfig {
    packageName = "com.muralex.data.network"

    defaultConfigs {
        buildConfigField(
            STRING,
            "RESTCOUNTRIES_API_KEY",
            localProperties.getProperty("RESTCOUNTRIES_API_KEY", "")
        )
    }
}