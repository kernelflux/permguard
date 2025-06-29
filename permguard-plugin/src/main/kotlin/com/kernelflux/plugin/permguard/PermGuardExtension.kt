package com.kernelflux.plugin.permguard

/**
 * Gradle extension for configuring the permguard plugin.
 * Users can specify which permissions to monitor, whitelist, and extra sensitive APIs.
 */
open class PermGuardExtension {
    /** Enable or disable plugin log output. */
    var enableLog: Boolean = true

    /**
     * List of sensitive permissions to monitor.
     * Covers all permissions that may trigger privacy/compliance review in Google Play and China app stores as of 2025.
     * Includes new/updated permissions from Android 13, 14, and 15.
     */
    var sensitivePermissions: List<String> = listOf(
        // Location
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_BACKGROUND_LOCATION",
        // Contacts
        "android.permission.READ_CONTACTS",
        "android.permission.WRITE_CONTACTS",
        // Phone/Device
        "android.permission.READ_PHONE_STATE",
        "android.permission.CALL_PHONE",
        "android.permission.READ_CALL_LOG",
        "android.permission.WRITE_CALL_LOG",
        "android.permission.ADD_VOICEMAIL",
        "android.permission.USE_SIP",
        "android.permission.PROCESS_OUTGOING_CALLS",
        // SMS/Messages
        "android.permission.SEND_SMS",
        "android.permission.RECEIVE_SMS",
        "android.permission.READ_SMS",
        "android.permission.RECEIVE_WAP_PUSH",
        "android.permission.RECEIVE_MMS",
        // Camera & Microphone
        "android.permission.CAMERA",
        "android.permission.RECORD_AUDIO",
        // Storage & Media (Android 13+)
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_MEDIA_IMAGES",
        "android.permission.READ_MEDIA_VIDEO",
        "android.permission.READ_MEDIA_AUDIO",
        // Calendar
        "android.permission.READ_CALENDAR",
        "android.permission.WRITE_CALENDAR",
        // Sensors & Health
        "android.permission.BODY_SENSORS",
        "android.permission.BODY_SENSORS_BACKGROUND", // Android 15+
        "android.permission.ACTIVITY_RECOGNITION",
        // Bluetooth/NFC
        "android.permission.BLUETOOTH",
        "android.permission.BLUETOOTH_ADMIN",
        "android.permission.BLUETOOTH_CONNECT",
        "android.permission.BLUETOOTH_SCAN",
        "android.permission.BLUETOOTH_ADVERTISE",
        "android.permission.NFC",
        // Device/Ad ID
        "com.google.android.gms.permission.AD_ID",
        // Notifications (Android 13+)
        "android.permission.POST_NOTIFICATIONS",
        "android.permission.USE_FULL_SCREEN_INTENT", // Android 15+
        // Screen capture/Projection
        "android.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION",
        // Health Connect (Android 14+)
        "android.permission.HEALTH_CONNECT"
    )

    /** Whitelist of package/class name prefixes to ignore. */
    var whiteList: List<String> = emptyList()

    /**
     * Extra sensitive APIs to monitor, in the form of (owner, name, descriptor).
     * This allows users to extend detection to custom or third-party APIs.
     */
    var extraSensitiveApis: List<Triple<String, String, String>> = emptyList()
}