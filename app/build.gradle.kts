import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.fphoenixcorneae.eyepetizer"
    compileSdk = 34

    defaultConfig {
        applicationId = namespace
        minSdk = 24
        targetSdk = 34
        versionCode = 100
        versionName = "1.0.0"

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "x86"))
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("../eyepetizer.jks")
            storePassword = "eyepetizer"
            keyAlias = "eyepetizer"
            keyPassword = "eyepetizer"
        }
        getByName("debug") {
            storeFile = file("../eyepetizer.jks")
            storePassword = "eyepetizer"
            keyAlias = "eyepetizer"
            keyPassword = "eyepetizer"
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    lint {
        checkDependencies = true
        checkReleaseBuilds = false
        abortOnError = false
        // 关闭检查的类型
        disable.addAll(listOf("ContentDescription", "SmallSp", "RtlSymmetry"))
    }
    packaging {
        resources {
            excludes.plus("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

// 输出文件
android.applicationVariants.all {
    outputs.all {
        // 输出 Apk
        if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
            outputFileName = "${project.name}_V${android.defaultConfig.versionName}_${buildType.name}_${
                SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
            }.apk"
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":compose-mvi")))
    implementation("com.github.FPhoenixCorneaE:Common:2.1.0")

    // kotlin
    val kotlinBom = platform("org.jetbrains.kotlin:kotlin-bom:1.8.0")
    implementation(kotlinBom)

    // compose
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // constraintlayout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
    implementation("androidx.paging:paging-compose:3.2.0")

    // startup
    implementation("androidx.startup:startup-runtime:1.1.1")

    // accompanist: https://google.github.io/accompanist/
    val accompanist = "0.30.1"
    implementation("com.google.accompanist:accompanist-insets:$accompanist")
    implementation("com.google.accompanist:accompanist-insets-ui:$accompanist")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist")
    implementation("com.google.accompanist:accompanist-permissions:$accompanist")
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanist")
    implementation("com.google.accompanist:accompanist-webview:$accompanist")
    implementation("com.google.accompanist:accompanist-pager:$accompanist")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanist")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanist")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanist")
    implementation("com.google.accompanist:accompanist-navigation-material:$accompanist")

    // coil: https://coil-kt.github.io/coil/compose/
    val coil = "2.4.0"
    implementation("io.coil-kt:coil-compose:$coil")
    implementation("io.coil-kt:coil-gif:$coil")
    implementation("io.coil-kt:coil-svg:$coil")
    implementation("io.coil-kt:coil-video:$coil")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // 视频播放器: https://github.com/CarGuo/GSYVideoPlayer
    implementation("com.github.CarGuo.GSYVideoPlayer:GSYVideoPlayer:v8.4.0-release-jitpack")

    // permissionx
    implementation("com.guolindev.permissionx:permissionx:1.7.1")

    // Compose版SmartRefreshLayout: https://github.com/RicardoJiang/compose-refreshlayout
    implementation("io.github.shenzhen2017:compose-refreshlayout:1.0.0")

    // 高效键值存储框架
    implementation("com.tencent:mmkv:1.3.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}