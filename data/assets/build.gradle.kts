plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    android {
        namespace = "com.exploramus.data.assets"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        withHostTest { }

        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    val xcfName = "dataAssetsKit"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            binaryOption("bundleId", "com.exploramus.data.assets")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:models"))
                implementation(project(":core:common"))
                implementation(project(":data:common"))

                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
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
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        iosMain {
            dependencies {}
        }
    }
}

// Sync assets to iOS (Android now points directly to commonMain/resources)
val syncAssetsToIos by tasks.registering(Copy::class) {
    from("src/commonMain/resources/")
    into("${rootProject.projectDir}/iosApp/iosApp/AppRawData/")
}

// In AGP 9.x KMP, we hook into the compile tasks directly if preBuild is missing
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    dependsOn(syncAssetsToIos)
}
