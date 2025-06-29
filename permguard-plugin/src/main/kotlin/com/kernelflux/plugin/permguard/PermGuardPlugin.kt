package com.kernelflux.plugin.permguard

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Main entry point for the permguard Gradle plugin.
 * Registers the ASM class visitor for all variants.
 */
class PermGuardPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("PermGuardPlugin applied to ${project.name}")
        // Create extension for user configuration
        val ext = project.extensions.create("permGuard", PermGuardExtension::class.java)
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            variant.instrumentation.transformClassesWith(
                PermGuardClassVisitorFactory::class.java,
                InstrumentationScope.ALL
            ) { params ->
                params.sensitiveApis.set(project.provider { PermGuardAsmConfig(ext).sensitiveApis })
                params.whiteList.set(project.provider { PermGuardAsmConfig(ext).whiteList })
            }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
        }
        project.afterEvaluate {
            PermGuardLogger.enableLog = ext.enableLog
        }
    }
}