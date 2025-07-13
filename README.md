# PermGuard 🔒

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Gradle Plugin](https://img.shields.io/badge/Gradle%20Plugin-1.0.0-green.svg)](https://plugins.gradle.org/plugin/com.kernelflux.plugin.permguard)
[![Android](https://img.shields.io/badge/Android-API%2021+-orange.svg)](https://developer.android.com/about/versions/android-5.0)

**PermGuard** is a powerful Android permission monitoring and compliance tool that helps developers track and manage sensitive API usage in their applications. It provides compile-time instrumentation to detect permission-related API calls and runtime monitoring capabilities.

## 🌟 Features

- **🔍 Comprehensive Permission Monitoring**: Tracks 50+ sensitive Android permissions including location, camera, contacts, storage, and more
- **⚡ Compile-time Instrumentation**: Uses ASM bytecode manipulation for efficient runtime monitoring
- **📱 Android 13-15 Support**: Covers latest permission changes and new privacy features
- **🎯 Whitelist Support**: Exclude specific packages or classes from monitoring
- **📊 Runtime Logging**: Detailed logging of sensitive API calls with class name masking
- **🔧 Customizable**: Extensible configuration for custom sensitive APIs
- **🚀 Zero Runtime Overhead**: Minimal performance impact with efficient bytecode instrumentation

## 📋 Supported Permissions

PermGuard monitors a comprehensive list of sensitive permissions including:

### Location & Device
- `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`, `ACCESS_BACKGROUND_LOCATION`
- `READ_PHONE_STATE`, `CALL_PHONE`, `READ_CALL_LOG`

### Media & Storage
- `CAMERA`, `RECORD_AUDIO`
- `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`
- `READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO`, `READ_MEDIA_AUDIO`

### Personal Data
- `READ_CONTACTS`, `WRITE_CONTACTS`
- `READ_CALENDAR`, `WRITE_CALENDAR`
- `READ_SMS`, `SEND_SMS`, `RECEIVE_SMS`

### Sensors & Health
- `BODY_SENSORS`, `BODY_SENSORS_BACKGROUND`
- `ACTIVITY_RECOGNITION`, `HEALTH_CONNECT`

### Connectivity
- `BLUETOOTH_*`, `NFC`
- `POST_NOTIFICATIONS`

And many more...

## 🚀 Quick Start

### 1. Add the Plugin

Add the PermGuard plugin to your project's `build.gradle.kts`:

```kotlin
plugins {
    id("com.kernelflux.plugin.permguard") version "1.0.0"
}
```

### 2. Configure PermGuard

Configure the plugin in your app's `build.gradle.kts`:

```kotlin
permGuard {
    // Enable/disable logging
    enableLog = true
    
    // Customize sensitive permissions (optional)
    sensitivePermissions = listOf(
        "android.permission.CAMERA",
        "android.permission.ACCESS_FINE_LOCATION"
    )
    
    // Whitelist packages to ignore
    whiteList = listOf(
        "com.example.safe.package",
        "androidx.core.content"
    )
    
    // Add custom sensitive APIs
    extraSensitiveApis = listOf(
        Triple("com.example.api", "sensitiveMethod", "(Ljava/lang/String;)V")
    )
}
```

### 3. Add Runtime Dependency

Add the PermGuard runtime library to your app's dependencies:

```kotlin
dependencies {
    implementation("com.kernelflux:permguard:1.0.0")
}
```

## 📖 Usage Examples

### Basic Configuration

```kotlin
// app/build.gradle.kts
plugins {
    id("com.android.application")
    id("com.kernelflux.plugin.permguard") version "1.0.0"
}

android {
    // Your Android configuration
}

permGuard {
    enableLog = true
    // Use default sensitive permissions list
}
```

### Advanced Configuration

```kotlin
permGuard {
    enableLog = true
    
    // Custom permission list
    sensitivePermissions = listOf(
        "android.permission.CAMERA",
        "android.permission.RECORD_AUDIO",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.READ_CONTACTS"
    )
    
    // Whitelist safe packages
    whiteList = listOf(
        "com.example.analytics",
        "androidx.core.content.ContextCompat"
    )
    
    // Monitor custom APIs
    extraSensitiveApis = listOf(
        Triple("com.example.api", "getUserData", "()Ljava/lang/String;"),
        Triple("com.example.api", "uploadData", "(Ljava/lang/String;)V")
    )
}
```

### Runtime Monitoring

The plugin automatically instruments your code to detect sensitive API calls. You can view the logs in Logcat:

```
D/PermGuard: Runtime: Sensitive API call detected: com.example.app.MainActivity#openCamera -> android.hardware.Camera.open()I
```

## 🏗️ Project Structure

```
permguard/
├── permguard-plugin/          # Gradle plugin implementation
│   ├── PermGuardPlugin.kt     # Main plugin entry point
│   ├── PermGuardExtension.kt  # Configuration extension
│   ├── PermGuardClassVisitor.kt # ASM class visitor
│   └── PermGuardMethodVisitor.kt # ASM method visitor
├── permguard/                 # Runtime library
│   ├── PermGuardRuntime.kt    # Runtime monitoring
│   ├── PermGuardLoggerManager.kt # Logging utilities
│   └── ClassNameMaskUtil.kt   # Class name masking
└── sample/                    # Sample application
    └── MainActivity.kt        # Usage examples
```

## 🔧 Configuration Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `enableLog` | Boolean | `true` | Enable/disable plugin logging |
| `sensitivePermissions` | List<String> | [50+ permissions] | Custom list of permissions to monitor |
| `whiteList` | List<String> | `emptyList()` | Package/class prefixes to ignore |
| `extraSensitiveApis` | List<Triple> | `emptyList()` | Custom APIs to monitor |

## 📱 Sample App

The project includes a sample app demonstrating various permission requests and API calls. Run the sample to see PermGuard in action:

```bash
./gradlew :sample:assembleDebug
./gradlew :sample:installDebug
```

## 🔍 How It Works

1. **Compile-time**: The Gradle plugin uses ASM to instrument your bytecode
2. **Runtime**: Instrumented code calls `PermGuardRuntime.onPermissionApiCall()` when sensitive APIs are invoked
3. **Logging**: Sensitive API calls are logged with masked class names for privacy
4. **Monitoring**: You can extend the runtime to add custom handling (analytics, alerts, etc.)

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Development Setup

```bash
git clone https://github.com/kernelflux/permguard.git
cd permguard
./gradlew build
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [ASM](https://asm.ow2.io/) - Bytecode manipulation framework
- [Android Gradle Plugin](https://developer.android.com/studio/build) - Build system integration
- [Android Permission System](https://developer.android.com/guide/topics/permissions/overview) - Permission documentation

---

**Made with ❤️ by KernelFlux Team** 