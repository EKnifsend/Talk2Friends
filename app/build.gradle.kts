plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.talk2friends"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.talk2friends"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    testImplementation("org.mockito:mockito-core:3.12.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    testImplementation("org.robolectric:robolectric:4.5.1")
    testImplementation("androidx.test:core:1.4.0")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    debugImplementation("androidx.fragment:fragment-testing:1.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:core:1.4.0") // Replace with the latest version
    androidTestImplementation("androidx.test.ext:junit:1.1.3") // Replace with the latest version
}