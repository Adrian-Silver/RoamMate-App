plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.roammate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.roammate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Navigation
//    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // ViewPager2
    implementation(libs.viewpager2)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Image loading
    implementation(libs.glide)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)


}