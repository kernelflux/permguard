package com.kernelflux.permguardsample

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private val permissions = listOf(
        Manifest.permission.CAMERA to "Camera",
        Manifest.permission.RECORD_AUDIO to "Record Audio",
        Manifest.permission.READ_CONTACTS to "Read Contacts",
        Manifest.permission.ACCESS_FINE_LOCATION to "Location",
        Manifest.permission.READ_EXTERNAL_STORAGE to "Read Storage",
        Manifest.permission.CALL_PHONE to "Call Phone",
        Manifest.permission.SEND_SMS to "Send SMS"
    )
    private lateinit var multiPermissionLauncher: ActivityResultLauncher<Array<String>>

    private fun openCamera() {
        try {
            val camera = android.hardware.Camera.open()
            camera.release()
            Toast.makeText(this, "Camera API called", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Camera open failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n", "DiscouragedApi", "InternalInsetResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multiPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result: Map<String, Boolean> ->
            // result: Map<权限名, 是否授予>
            result.forEach { (permission, granted) ->
                Log.d("PermGuardDebug_x", "$permission granted: $granted")
                if(granted){
                    openCamera()
                }else{
                    Toast.makeText(this, "$permission granted: ${false}", Toast.LENGTH_SHORT).show()
                }


            }
        }


        val scrollView = ScrollView(this)
        val statusBarHeight = resources.getDimensionPixelSize(
            resources.getIdentifier("status_bar_height", "dimen", "android")
        )
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32 + statusBarHeight, 32, 32)
        }

        // 动态注册 launcher 并创建按钮
        permissions.forEach { (perm, label) ->

            val btn = MaterialButton(this).apply {
                text = "Request $label"
                setPadding(0, 32, 0, 32)
                textSize = 18f
                cornerRadius = 32
                setOnClickListener {
                    requestPermission(perm, label)
                }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 32)
                }
            }
            layout.addView(btn)
        }

        scrollView.addView(layout)
        setContentView(scrollView)
    }

    private fun requestPermission(permission: String, label: String) {
        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "$label permission already granted", Toast.LENGTH_SHORT).show()
                openCamera()
            }
            else -> {
                multiPermissionLauncher.launch(arrayOf(permission))
            }
        }
    }
}