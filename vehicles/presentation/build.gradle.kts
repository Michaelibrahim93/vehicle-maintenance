import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp) // Apply KSP
    alias(libs.plugins.dagger.hilt.android) // Apply Hilt
}

android {
    namespace = "com.mike.vehicles.presentation"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_18)
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
            freeCompilerArgs.add("-Xopt-in=kotlin.RequiresOptIn")
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //modules
    implementation(project(":core:resources"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
    implementation(project(":vehicles:domian"))

    // Type Safe Navigation
    implementation(libs.navigation.compose)
    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler) // Use ksp for Hilt's annotation processor
    ksp(libs.hilt.compiler) // For androidx.hilt extensions

    // Timber Logs
    implementation(libs.timber)

    //Compose Image Loader
    implementation(libs.coil.compose)
}