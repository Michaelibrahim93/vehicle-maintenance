import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp) // Apply KSP
    alias(libs.plugins.dagger.hilt.android) // Apply Hilt
    alias(libs.plugins.junit5)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_18)
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
        freeCompilerArgs.add("-Xopt-in=kotlin.RequiresOptIn")
    }
}

android {
    namespace = "com.mike.maintenancealarm"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mike.maintenancealarm"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "0.0.04"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("debugKeystore") {
            storeFile = file("../key/debug-key.jks")
            keyAlias = "android-alias"
            keyPassword = "pass1234"
            storePassword = "pass1234"
        }
    }

    applicationVariants.all {
        outputs.filterIsInstance<com.android.build.gradle.internal.api.BaseVariantOutputImpl>()
            .forEach { output ->
                val variant = output
                val buildTypeName = variant.baseName
                val versionName = versionName
                val versionCode = versionCode
                val extension = variant.outputFile.extension

                val newName = "app-${buildTypeName}-v${versionName}(${versionCode}).$extension"
                output.outputFileName = newName
            }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debugKeystore")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            signingConfig = signingConfigs.getByName("debugKeystore")
            isMinifyEnabled = true
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

    buildFeatures {
        compose = true
    }

    ksp {
        //room schemaLocation
        arg("room.schemaLocation", "${projectDir}/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)

    // AndroidX Lifecycle
    implementation(libs.lifecycle.viewmodel.ktx)

    // Type Safe Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler) // Use ksp for Hilt's annotation processor
    ksp(libs.hilt.compiler) // For androidx.hilt extensions

    //Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler) // Use KSP for Room's annotation processor

    // Timber Logs
    implementation(libs.timber)

    //Compose Image Loader
    implementation(libs.coil.compose)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ndk)
    implementation(libs.firebase.analytics)

    // unit tests
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.leakcanary.android)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
}