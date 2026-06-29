plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.exploramus.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets {
        getByName("main") {
            kotlin.directories.add("src/androidMain/kotlin")
            res.directories.add("src/androidMain/res")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }

    defaultConfig {
        applicationId = "com.exploramus.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(projects.shared)
    implementation(project(":core:common"))
    implementation(project(":data:repository"))
    implementation(project(":di"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle)
    implementation(libs.android.material)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.adaptive)
    implementation(libs.adaptive.layout)
    implementation(libs.adaptive.navigation)

    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.ui)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)

    implementation(libs.coil.compose)

    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.androidx.compose.uitest.manifest)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.compose.uitest.junit4)
}

tasks.configureEach {
    if (name.contains("merge") && name.contains("Assets")) {
        dependsOn(":data:assets:syncAssetsToAndroid")
    }
}
