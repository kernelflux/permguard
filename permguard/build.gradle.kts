plugins {
    alias(libs.plugins.android.library)
    id("com.kernelflux.android.module")
}

android {
    namespace = "com.kernelflux.permguard"

    buildTypes {
        debug {
            buildConfigField("String", "PERMGUARD_MASK_STRATEGY", "\"FULL\"")
        }
        release {
            buildConfigField("String", "PERMGUARD_MASK_STRATEGY", "\"HASH\"")
        }
    }
}

dependencies {
}