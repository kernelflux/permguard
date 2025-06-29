package com.kernelflux.plugin.permguard

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input

/**
 * Factory for creating the ASM class visitor for each class.
 * Passes sensitive API and whitelist configuration to the visitor.
 */
abstract class PermGuardClassVisitorFactory : AsmClassVisitorFactory<PermGuardClassVisitorFactory.Params> {
    interface Params : InstrumentationParameters {
        @get:Input
        val sensitiveApis: ListProperty<Triple<String, String, String>>
        @get:Input
        val whiteList: ListProperty<String>
    }

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return PermGuardClassVisitor(
            nextClassVisitor,
            parameters.get().sensitiveApis.get(),
            parameters.get().whiteList.get()
        )
    }

    override fun isInstrumentable(classData: com.android.build.api.instrumentation.ClassData): Boolean = true
}