package com.kernelflux.permguard


object PermGuardRuntime {

    /**
     * This method will be called by ASM instrumentation code to log or handle sensitive API calls.
     *
     * @param className   The name of the current class
     * @param methodName  The name of the current method
     * @param owner       The owner of the called method
     * @param name        The name of the called method
     * @param descriptor  The descriptor of the called method
     */
    @JvmStatic
    fun onPermissionApiCall(
        className: String,
        methodName: String,
        owner: String,
        name: String,
        descriptor: String
    ) {
        val maskedClassName = maskClassName(className)
        PermGuardLoggerManager.d(
            "Runtime",
            "Sensitive API call detected: $maskedClassName#$methodName -> $owner.$name$descriptor"
        )

    }

}