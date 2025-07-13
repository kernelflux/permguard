package com.kernelflux.permguard

enum class ClassNameMaskStrategy {
    FULL,
    SIMPLE,
    HASH,
    CUSTOM_TAG
}

object PermGuardMaskConfig {
    var classNameStrategy: ClassNameMaskStrategy = ClassNameMaskStrategy.HASH
    var customTag: String = "SensitiveClass"
}

fun String.md5(): String {
    val md = java.security.MessageDigest.getInstance("MD5")
    val bytes = md.digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

fun maskClassName(className: String): String {
    return when (PermGuardMaskConfig.classNameStrategy) {
        ClassNameMaskStrategy.FULL -> className
        ClassNameMaskStrategy.SIMPLE -> className.substringAfterLast('.')
        ClassNameMaskStrategy.HASH -> className.md5()
        ClassNameMaskStrategy.CUSTOM_TAG -> PermGuardMaskConfig.customTag
    }
}