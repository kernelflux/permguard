package com.kernelflux.permguardsample

import android.app.Application
import com.kernelflux.permguard.BuildConfig
import com.kernelflux.permguard.ClassNameMaskStrategy
import com.kernelflux.permguard.PermGuardMaskConfig

class PermGuardApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initPermGuardMaskStrategy()
    }

    fun initPermGuardMaskStrategy() {
        val strategy = System.getProperty("permguardMaskStrategy")
            ?: if(BuildConfig.DEBUG) BuildConfig.PERMGUARD_MASK_STRATEGY else  "HASH"
        PermGuardMaskConfig.classNameStrategy = when (strategy) {
            "FULL" -> ClassNameMaskStrategy.FULL
            "SIMPLE" -> ClassNameMaskStrategy.SIMPLE
            "HASH" -> ClassNameMaskStrategy.HASH
            "CUSTOM_TAG" -> ClassNameMaskStrategy.CUSTOM_TAG
            else -> ClassNameMaskStrategy.HASH
        }
    }

}