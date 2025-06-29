package com.kernelflux.plugin.permguard

/**
 * Simple logger for the permguard plugin.
 * Controlled by the enableLog flag in the extension.
 */
object PermGuardLogger {
    var enableLog = true

    fun log(msg: String) {
        if (enableLog) println("[PermGuard] $msg")
    }
}