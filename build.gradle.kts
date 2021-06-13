import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath ("com.google.gms:google-services:4.3.8")
        classpath(Libraries.kotlinGradle)
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath(kotlin("serialization", Versions.kotlin))

    }
}

allprojects {
    buildscript {
        val kotlin_version by extra("1.4.21")
        repositories {
            mavenCentral()
            google()
            jcenter()
            gradlePluginPortal()
        }
    }
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
        maven(File(rootDir, "libs"))
        jcenter()
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = Versions.java.toString()
        targetCompatibility = Versions.java.toString()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = Versions.java.toString()
            freeCompilerArgs = listOf(
                    "-progressive",
                    "-Xjvm-default=enable",
                    "-Xallow-result-return-type",
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
                    "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-Xopt-in=kotlin.time.ExperimentalTime",
                    "-Xopt-in=kotlinx.coroutines.ObsoleteCoroutinesApi",
                    "-Xopt-in=kotlinx.coroutines.FlowPreview"
            )
        }
    }
}



