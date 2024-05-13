import org.gradle.internal.impldep.bsh.commands.dir
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt




plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.nfkhusq"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nfkhusq"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("com.google.android.gms:play-services-wallet:19.3.0")
    implementation("com.google.ai.client.generativeai:generativeai:0.5.0")
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha04")
    implementation("androidx.compose.material:material-icons-core:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:1.2.0")
    implementation("androidx.activity:activity-compose:1.3.0-alpha06")
    implementation ("com.google.accompanist:accompanist-permissions:0.33.1-alpha")
    implementation ("androidx.compose.runtime:runtime-livedata:1.2.0") // For LiveData integration
    implementation ("androidx.compose.runtime:runtime-rxjava3:1.2.0") // For RxJava3 support
// Kotlin Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:<version>")
// Kotlin Coroutines Android support
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:<version>")

    implementation ("androidx.compose.runtime:runtime-livedata:<version>")

    implementation ("com.google.accompanist:accompanist-permissions:0.29.0-alpha")
    implementation ("com.jakewharton.timber:timber:5.0.1")
    implementation ("com.jakewharton.timber:timber:5.0.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // For AppWidgets support
    implementation( "androidx.glance:glance-appwidget:1.0.0" )

    // For interop APIs with Material 2
    implementation( "androidx.glance:glance-material:1.0.0" )

    // For interop APIs with Material 3
    implementation( "androidx.glance:glance-material3:1.0.0" )
    val room_version = "2.5.0"

    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")


    val koin_android_version= "3.3.2"
    val koin_android_compose_version= "3.4.1"

    implementation ("io.insert-koin:koin-android:$koin_android_version")
    implementation ("io.insert-koin:koin-android-compat:$koin_android_version")
    implementation ("io.insert-koin:koin-androidx-workmanager:$koin_android_version")
    implementation ("io.insert-koin:koin-androidx-navigation:$koin_android_version")
    implementation ("io.insert-koin:koin-androidx-compose:$koin_android_compose_version")

    //testing

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}