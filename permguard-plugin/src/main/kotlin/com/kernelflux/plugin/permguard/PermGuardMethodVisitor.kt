package com.kernelflux.plugin.permguard
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * ASM method visitor that detects sensitive API calls and logs them.
 * You can extend this to insert runtime SDK calls or other logic.
 */
class PermGuardMethodVisitor(
    mv: MethodVisitor,
    private val className: String,
    private val methodName: String,
    private val sensitiveApis: List<Triple<String, String, String>>,
    private val whiteList: List<String>
) : MethodVisitor(Opcodes.ASM9, mv) {

    override fun visitMethodInsn(
        opcode: Int, owner: String, name: String, descriptor: String, isInterface: Boolean
    ) {
        println("visitMethodInsn: $owner.$name$descriptor in $className#$methodName")

        //[PermGuard] Sensitive API call detected:
        //com.kernelflux.permguardsample.MainActivity#openCamera -> android/hardware/Camera.open()Landroid/hardware/Camera;

        // Skip whitelisted classes/packages
        if (whiteList.any { className.startsWith(it) }) {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
            return
        }
        // Detect sensitive API call
        if (sensitiveApis.any { it.first == owner && it.second == name && it.third == descriptor }) {
            PermGuardLogger.log("Sensitive API call detected: $className#$methodName -> $owner.$name$descriptor")
             mv.visitLdcInsn(className)
             mv.visitLdcInsn(methodName)
             mv.visitLdcInsn(owner)
             mv.visitLdcInsn(name)
             mv.visitLdcInsn(descriptor)
             mv.visitMethodInsn(
                 Opcodes.INVOKESTATIC,
                 "com/kernelflux/permguard/PermGuardRuntime",
                 "onPermissionApiCall",
                 "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                 false
             )
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }
}