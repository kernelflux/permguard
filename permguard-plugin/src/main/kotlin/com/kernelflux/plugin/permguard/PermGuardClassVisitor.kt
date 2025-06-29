package com.kernelflux.plugin.permguard

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * ASM class visitor that delegates to PermGuardMethodVisitor for each method.
 */
class PermGuardClassVisitor(
    cv: ClassVisitor,
    private val sensitiveApis: List<Triple<String, String, String>>,
    private val whiteList: List<String>
) : ClassVisitor(Opcodes.ASM9, cv) {
    private var className: String = ""

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
        className = name.replace('/', '.')
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int, name: String, desc: String, signature: String?, exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = super.visitMethod(access, name, desc, signature, exceptions)
        return PermGuardMethodVisitor(
            mv, className, name, sensitiveApis, whiteList
        )
    }
}