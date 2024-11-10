plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    //id("dagger.hilt.android.plugin") version "2.48"

    //hilt 추가 내용
    //id("com.google.dagger.hilt.android") version "2.48" apply false
    //alias(libs.plugins.dagger.hilt.android)

    
    kotlin("plugin.serialization") version "1.5.0"

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.internproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.internproject"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "dagger.hilt.android.testing.HiltTestRunner"

        kapt {
//            javacOptions {
//                // These options are normally set automatically via the Hilt Gradle plugin, but we
//                // set them manually to workaround a bug in the Kotlin 1.5.20
//                option("-Adagger.fastInit=ENABLED")
//                option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
//            }

            correctErrorTypes = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        viewBinding {
            enable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    
    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")

    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    //glide
    implementation("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    //gson
    implementation("com.google.code.gson:gson:2.11.0")
}