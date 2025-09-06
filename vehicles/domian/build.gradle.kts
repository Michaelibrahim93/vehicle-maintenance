import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp) // Apply KSP
    alias(libs.plugins.dagger.hilt.android) // Apply Hilt
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.mike.vehicles.domian"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_18)
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
            freeCompilerArgs.add("-Xopt-in=kotlin.RequiresOptIn")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler) // Use ksp for Hilt's annotation processor
    ksp(libs.hilt.compiler) // For androidx.hilt extensions

    // Timber Logs
    implementation(libs.timber)

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //core
    implementation(project(":core:domain"))
    testImplementation(project(":vehicles:test-utils"))

    // unit tests
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}