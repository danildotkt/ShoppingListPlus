plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {

    namespace = "io.avdev.shoppinglistplus"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.avdev.shoppinglistplus"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    data()
    domain()

    room()
    daggerHilt()

    implementation(Dependencies.yandexMobileAds)

    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.lifecycleViewModelKtx)
    implementation(Dependencies.kotlinxCoroutinesAndroid)

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
}