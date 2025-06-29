package com.kernelflux.plugin.permguard

/**
 * Internal config for the permguard plugin.
 * Maps permissions to sensitive APIs and merges with user-supplied extra APIs.
 */
class PermGuardAsmConfig(
    ext: PermGuardExtension
) {
    /**
     * Built-in mapping from permission to related sensitive APIs (owner, name, desc).
     * This list is based on Android SDK, Google Play, and China app store compliance requirements.
     * You can extend this list as needed.
     */
    private val permissionApiMap: Map<String, List<Triple<String, String, String>>> = mapOf(
        // Location
        "android.permission.ACCESS_FINE_LOCATION" to listOf(
            Triple("android/location/LocationManager", "requestLocationUpdates", "(Ljava/lang/String;JFLandroid/location/LocationListener;)V"),
            Triple("android/location/LocationManager", "getLastKnownLocation", "(Ljava/lang/String;)Landroid/location/Location;"),
            Triple("android/location/LocationManager", "getCurrentLocation", "(Ljava/lang/String;Ljava/util/concurrent/Executor;Landroid/location/LocationManager\$LocationListener;)V")
        ),
        // Contacts
        "android.permission.READ_CONTACTS" to listOf(
            Triple("android/content/ContentResolver", "query", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;")
        ),
        // Phone
        "android.permission.READ_PHONE_STATE" to listOf(
            Triple("android/telephony/TelephonyManager", "getDeviceId", "()Ljava/lang/String;"),
            Triple("android/telephony/TelephonyManager", "getImei", "()Ljava/lang/String;"),
            Triple("android/telephony/TelephonyManager", "getMeid", "()Ljava/lang/String;"),
            Triple("android/telephony/TelephonyManager", "getSubscriberId", "()Ljava/lang/String;"),
            Triple("android/telephony/TelephonyManager", "getSimSerialNumber", "()Ljava/lang/String;")
        ),
        // SMS
        "android.permission.READ_SMS" to listOf(
            Triple("android/content/ContentResolver", "query", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;")
        ),
        // Camera
        "android.permission.CAMERA" to listOf(
            Triple("android/hardware/Camera", "open", "()Landroid/hardware/Camera;"),
            Triple("android/hardware/camera2/CameraManager", "openCamera", "(Ljava/lang/String;Landroid/hardware/camera2/CameraDevice\$StateCallback;Landroid/os/Handler;)V")
        ),
        // Microphone
        "android.permission.RECORD_AUDIO" to listOf(
            Triple("android/media/MediaRecorder", "start", "()V"),
            Triple("android/media/AudioRecord", "<init>", "(IIIII)V")
        ),
        // Storage & Media
        "android.permission.READ_EXTERNAL_STORAGE" to listOf(
            Triple("android/content/ContentResolver", "openInputStream", "(Landroid/net/Uri;)Ljava/io/InputStream;")
        ),
        "android.permission.WRITE_EXTERNAL_STORAGE" to listOf(
            Triple("android/content/ContentResolver", "openOutputStream", "(Landroid/net/Uri;)Ljava/io/OutputStream;")
        ),
        "android.permission.READ_MEDIA_IMAGES" to listOf(
            Triple("android/content/ContentResolver", "openInputStream", "(Landroid/net/Uri;)Ljava/io/InputStream;")
        ),
        "android.permission.READ_MEDIA_VIDEO" to listOf(
            Triple("android/content/ContentResolver", "openInputStream", "(Landroid/net/Uri;)Ljava/io/InputStream;")
        ),
        "android.permission.READ_MEDIA_AUDIO" to listOf(
            Triple("android/content/ContentResolver", "openInputStream", "(Landroid/net/Uri;)Ljava/io/InputStream;")
        ),
        // Calendar
        "android.permission.READ_CALENDAR" to listOf(
            Triple("android/content/ContentResolver", "query", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;")
        ),
        // Sensors
        "android.permission.BODY_SENSORS" to listOf(
            Triple("android/hardware/SensorManager", "getSensorList", "(I)Ljava/util/List;")
        ),
        "android.permission.BODY_SENSORS_BACKGROUND" to listOf(
            Triple("android/hardware/SensorManager", "getSensorList", "(I)Ljava/util/List;")
        ),
        // Bluetooth/NFC
        "android.permission.BLUETOOTH_CONNECT" to listOf(
            Triple("android/bluetooth/BluetoothDevice", "connectGatt", "(Landroid/content/Context;ZZLandroid/bluetooth/BluetoothGattCallback;)Landroid/bluetooth/BluetoothGatt;")
        ),
        "android.permission.BLUETOOTH_SCAN" to listOf(
            Triple("android/bluetooth/BluetoothAdapter", "startDiscovery", "()Z")
        ),
        // Notification
        "android.permission.POST_NOTIFICATIONS" to listOf(
            Triple("android/app/NotificationManager", "notify", "(ILandroid/app/Notification;)V"),
            Triple("android/app/NotificationManager", "notify", "(Ljava/lang/String;ILandroid/app/Notification;)V")
        ),
        // Full screen notification (Android 15+)
        "android.permission.USE_FULL_SCREEN_INTENT" to listOf(
            Triple("android/app/Notification\$Builder", "setFullScreenIntent", "(Landroid/app/PendingIntent;Z)Landroid/app/Notification\$Builder;")
        ),
        // Screen capture/Projection
        "android.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION" to listOf(
            Triple("android/media/projection/MediaProjectionManager", "getMediaProjection", "(ILandroid/content/Intent;)Landroid/media/projection/MediaProjection;")
        ),
        // Health Connect (Android 14+)
        "android.permission.HEALTH_CONNECT" to listOf(
            Triple("android/health/connect/HealthConnectManager", "queryRecords", "(Landroid/health/connect/ReadRecordsRequest;)Landroid/health/connect/ReadRecordsResponse;")
        )
        // ...extend as needed
    )
    /**
     * The final list of sensitive APIs to monitor, including built-in and user-supplied.
     */
    val sensitiveApis: List<Triple<String, String, String>> =
        ext.sensitivePermissions.flatMap { permissionApiMap[it] ?: emptyList() } + ext.extraSensitiveApis

    /** Whitelist of package/class name prefixes to ignore. */
    val whiteList: List<String> = ext.whiteList
}