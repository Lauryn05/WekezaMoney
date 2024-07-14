plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.cns.wekezamoney"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cns.wekezamoney"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            jvmTarget = "1.8"
        }

        buildFeatures {
            viewBinding = true
        }

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    dependencies {
        implementation("androidx.core:core-ktx:1.10.1") {
            exclude(group = "com.intellij", module = "annotations")
        }
        implementation(libs.androidx.appcompat.v170)
        implementation(libs.material.v180)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.lifecycle.livedata.ktx.v251)
        implementation(libs.androidx.lifecycle.viewmodel.ktx.v251)
        implementation(libs.androidx.navigation.fragment.ktx.v253)
        implementation(libs.androidx.navigation.ui.ktx.v253)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        implementation(libs.androidx.room.runtime.v243)
        //noinspection KaptUsageInsteadOfKsp
        kapt(libs.androidx.room.compiler.v243)
        implementation(libs.androidx.room.ktx.v243)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)
    }

    kapt {
        correctErrorTypes = true
    }
}