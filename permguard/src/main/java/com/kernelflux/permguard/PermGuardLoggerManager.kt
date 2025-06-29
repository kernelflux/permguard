package com.kernelflux.permguard

import android.util.Log

/**
 * Logger interface for SDK internal use.
 * Host app can implement and inject their own logger.
 */
interface SdkLogger {
    fun d(tag: String, msg: String)
    fun i(tag: String, msg: String)
    fun w(tag: String, msg: String)
    fun e(tag: String, msg: String, throwable: Throwable? = null)
    fun v(tag: String, msg: String)


    fun d(tag: String, format: String, vararg args: Any?) = d(tag, format.format(*args))
    fun i(tag: String, format: String, vararg args: Any?) = i(tag, format.format(*args))
    fun w(tag: String, format: String, vararg args: Any?) = w(tag, format.format(*args))
    fun e(tag: String, format: String, vararg args: Any?) = e(tag, format.format(*args))
    fun v(tag: String, format: String, vararg args: Any?) = v(tag, format.format(*args))
}

/**
 * Default logger implementation: output to Logcat.
 */
class DefaultSdkLogger : SdkLogger {
    private val prefix = "PermGuardSDK"

    override fun d(tag: String, msg: String) {
        Log.d("$prefix/$tag", msg)
    }

    override fun i(tag: String, msg: String) {
        Log.i("$prefix/$tag", msg)
    }

    override fun w(tag: String, msg: String) {
        Log.w("$prefix/$tag", msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        Log.e("$prefix/$tag", msg, throwable)
    }

    override fun v(tag: String, msg: String) {
        Log.v("$prefix/$tag", msg)
    }
}

/**
 * Logger manager for SDK.
 * Use SdkLoggerManager.d(...) etc. in SDK code.
 * Host app can inject their own logger via setLogger().
 */
object PermGuardLoggerManager {
    @Volatile
    private var logger: SdkLogger = DefaultSdkLogger()
    @Volatile
    private var enabled: Boolean = true

    fun setLogger(customLogger: SdkLogger?) {
        if (customLogger != null) {
            logger = customLogger
        }
    }

    fun setEnabled(enable: Boolean) {
        enabled = enable
    }

    fun d(tag: String, msg: String) {
        if (enabled) logger.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (enabled) logger.i(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (enabled) logger.w(tag, msg)
    }

    fun e(tag: String, msg: String, throwable: Throwable? = null) {
        if (enabled) logger.e(tag, msg, throwable)
    }

    fun v(tag: String, msg: String) {
        if (enabled) logger.v(tag, msg)
    }


    fun d(tag: String, format: String, vararg args: Any?) {
        if (enabled) logger.d(tag, format, *args)
    }

    fun i(tag: String, format: String, vararg args: Any?) {
        if (enabled) logger.i(tag, format, *args)
    }

    fun w(tag: String, format: String, vararg args: Any?) {
        if (enabled) logger.w(tag, format, *args)
    }

    fun e(tag: String, format: String, vararg args: Any?) {
        if (enabled) logger.e(tag, format, *args)
    }

    fun v(tag: String, format: String, vararg args: Any?) {
        if (enabled) logger.v(tag, format, *args)
    }
}