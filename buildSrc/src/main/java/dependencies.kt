import org.gradle.api.JavaVersion

object Versions {

    // Build tools and SDK
    const val buildTools = "29.0.3"
    const val compileSdk = 30
    const val gradlePlugin = "4.1.1"
    const val kotlin = "1.5.10"
    const val minSdk = 21
    const val targetSdk = 30
    const val ndk = "21.3.6528147"

    // Android libraries
    const val appcompat = "1.2.0"
    const val arch = "2.1.0"
    const val cardview = "1.0.0"
    const val constraintlayout = "2.0.4"
    const val coordinatorLayout = "1.1.0"
    const val core = "1.3.1"
    //const val fragment = "1.2.5"
    const val fragment = "1.3.0-rc02"
    const val lifecycle = "2.2.0"
    const val navigation = "2.3.0"
    const val recyclerview = "1.1.0"
    const val room = "2.3.0-alpha04"

    // Libraries
    const val autoValue = "1.6.6"
    const val dagger = "2.27"
    const val daggerAssisted = "0.5.2"
    const val epoxy = "4.0.0"
    const val koin = "2.2.2"
    const val koinArchitecture = "0.8.2"
    const val kotlinCoroutines = "1.4.1"
    const val lottie = "3.4.0"
    const val moshi = "1.11.0"
    const val moshiConverter = "2.9.0"
    const val multidex = "2.0.1"
    const val mvrx = "2.0.0-beta3"
    const val picasso = "2.8"
    const val retrofit = "2.9.0"
    const val rxAndroid = "2.1.1"
    const val rxJava = "2.2.9"
    const val logginInterceptor = "4.9.0"
    const val gson = "2.9.0"
    const val materialDesign = "1.3.0-alpha01"
    const val fresco = "2.3.0"
    const val exoplayer = "2.11.7"
    const val work = "2.5.0"
    const val shimmer = "0.5.0"
    const val getSocial = "6.32.2"
    const val swipeRefresh = "1.1.0"
    const val compressor = "3.0.0"
    const val glideVersion = "4.11.0"
    const val labratoryVersion = "0.9.7"

    const val versionsGradlePlugin = "0.36.0"
    const val detekt = "1.15.0"
    @JvmField
    val java = JavaVersion.VERSION_1_8
    // Instrumented testing libraries
    const val espresso = "3.2.0"

    // Testing libraries
    const val junit = "4.13"
    const val junitExt = "1.1.1"
    const val mockito = "2.25.1"
    const val mockitoKotlin = "2.2.0"
    const val mockk = "1.9.3"
    const val robolectric = "4.3.1"
    const val testCore = "1.2.0"
    const val liveData = "2.2.0"
    const val retrofitCoroutinesAdapter = "0.9.2"
    const val customTab = "1.3.0"
    const val prDownloader = "0.6.0"
    const val snapHelper = "2.2.1"
    const val fastAdapterVersion = "5.3.2"

    const val facebookSdkVersion = "8.2.0"
    const val twitterSdkVersion = "3.3.0"
    const val playServiceVersion = "19.0.0"

    const val phoneLibVersion = "8.12.5"

    const val jacksonVersion = "2.12.0"


}

object AnnotationProcessors {
    const val autoValue = "com.google.auto.value:auto-value:${Versions.autoValue}"
    const val dagger = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAssisted = "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.daggerAssisted}"
    const val epoxy = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
    const val lifecycle = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val moshi = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val room = "androidx.room:room-compiler:${Versions.room}"

}

object Libraries {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val autoValue = "com.google.auto.value:auto-value-annotations:${Versions.autoValue}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayout}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAssisted = "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.daggerAssisted}"
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
    const val espressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragment}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragment}"
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomRxJava = "androidx.room:room-rxjava2:${Versions.room}"
    const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val mvrx = "com.airbnb.android:mvrx:${Versions.mvrx}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.logginInterceptor}"
    const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.gson}"
    const val coroutineAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"
    const val koinCore = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinArchitecture = "org.koin:koin-android-architecture:${Versions.koinArchitecture}"
    const val googleMaterial = "com.google.android.material:material:${Versions.materialDesign}"
    const val facebookFresco = "com.facebook.fresco:fresco:${Versions.fresco}"
    const val exoplayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayer}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val prDownloader = "com.mindorks.android:prdownloader:${Versions.prDownloader}"

    const val workRuntime = "androidx.work:work-runtime:${Versions.work}"
    const val workRuntimeKtx = "androidx.work:work-runtime-ktx:${Versions.work}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
    const val customTab = "androidx.browser:browser:${Versions.customTab}"

    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    const val getSocial = "im.getsocial:getsocial-core:${Versions.getSocial}"

    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"

    const val snapHelper = "com.github.rubensousa:gravitysnaphelper:${Versions.snapHelper}"
    const val epoxyDataBinding = "com.airbnb.android:epoxy-databinding:${Versions.epoxy}"
    const val ktxViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    const val serializationCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1"
    const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1"
    const val stdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.logginInterceptor}"
    const val converterKotlinxSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    const val coroutinesRx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:1.4.2"
    const val sdp = "com.intuit.sdp:sdp-android:1.0.6"


    const val core = "io.mehow.laboratory:laboratory:${Versions.labratoryVersion}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    const val googlePlayServices = "com.google.gms:google-services:4.3.4"

    private const val version = "0.9.7"


    const val startup = "androidx.startup:startup-runtime:1.0.0"
    const val videocache = "com.danikula:videocache:2.7.1"

    const val javaxAnnotation = "javax.annotation:javax.annotation-api:1.3.2"

    const val jacksonCore = "com.fasterxml.jackson.core:jackson-core:${Versions.jacksonVersion}"
    const val jacksonAnnotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jacksonVersion}"
    const val jjwt = "io.jsonwebtoken:jjwt:0.9.1"
    const val jwtDecode = "com.auth0.android:jwtdecode:2.0.0"

    const val constriaintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val design = "com.google.android.material:material:1.3.0-rc01"
    const val coreLibraryDesugaring = "com.android.tools:desugar_jdk_libs:1.1.5"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val lifcycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val workManagerRuntime = "androidx.work:work-runtime:${Versions.work}"
    const val activityKtx = "androidx.activity:activity-ktx:1.2.0-rc01"
}


object InstrumentedTestLibraries {
    const val core = "androidx.test:core:${Versions.testCore}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val junit = "androidx.test.ext:junit:${Versions.junitExt}"
}

object TestLibraries {
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val roboeletric = "org.robolectric:robolectric:${Versions.robolectric}"
}

object Retrofit {
    private const val version = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$version"
    const val adapterRxJava = "com.squareup.retrofit2:adapter-rxjava:$version"
    const val adapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:$version"
    const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    const val converterScalars = "com.squareup.retrofit2:converter-scalars:$version"
    const val converterKotlinxSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
}

object PlayServices {
    private const val castVersion = "19.0.0"
    const val castFramework = "com.google.android.gms:play-services-cast-framework:$castVersion"
    const val cast = "com.google.android.gms:play-services-cast:$castVersion"
    const val analytics = "com.google.android.gms:play-services-analytics:17.0.0"
    const val auth = "com.google.android.gms:play-services-auth:19.0.0"
    const val location = "com.google.android.gms:play-services-location:18.0.0"
    const val ads = "com.google.android.gms:play-services-ads:20.0.0"
    const val adsIdentifier = "com.google.android.gms:play-services-ads-identifier:17.0.0"
    const val tagManager = "com.google.android.gms:play-services-tagmanager:17.0.0"
    const val playCore = "com.google.android.play:core:1.10.0"
}

object RxJava {
    const val rxAndroid1 = "io.reactivex:rxandroid:1.2.1"
    const val rxJava1 = "io.reactivex:rxjava:1.3.8"
    const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxJava2 = "io.reactivex.rxjava2:rxjava:2.2.21"
}
