import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    id("java-library")
    id("com.gradle.plugin-publish") version "1.3.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


tasks.withType(KotlinCompile::class.java) {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}


dependencies {
    //noinspection UseTomlInstead
    implementation("org.ow2.asm:asm:9.8")
    //noinspection UseTomlInstead
    implementation("org.ow2.asm:asm-commons:9.8")
    compileOnly("com.android.tools.build:gradle:8.11.0")
    implementation(gradleApi())
}


group = "com.kernelflux.plugin.permguard"
version = "1.0.0"

gradlePlugin {
    website = "https://github.com/kernelflux/permguard"
    vcsUrl = "https://github.com/kernelflux/permguard"
    plugins {
        create("permguardPlugin") {
            id = group.toString()
            implementationClass = "com.kernelflux.plugin.permguard.PermGuardPlugin"
            displayName = "Gradle Permguard Plugin"
            description = "Block and monitor sensitive permission usage in Android."
            tags = listOf("optimizes", "permguard")
        }
    }
}

