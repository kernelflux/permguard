plugins {
    alias(libs.plugins.android.application)
    id("com.kernelflux.android.module")
    id("com.kernelflux.plugin.permguard")
}

android {
    namespace = "com.kernelflux.permguardsample"
}

dependencies {
    implementation(project(":permguard"))
}