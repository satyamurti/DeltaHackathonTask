package com.mrspd.photoeditor

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mrspd.photoeditor.Permissions.PermissionResultCallback
import com.mrspd.photoeditor.Permissions.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback,
    PermissionResultCallback {
    var permissions: ArrayList<String> = ArrayList()
    var permissionUtils: PermissionUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionUtils = PermissionUtils(this)

        permissions.add(Manifest.permission.CAMERA)
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        permissionUtils!!.checkPermission(permissions,"The app needs external storage and camera permissions",1)
        navView.setupWithNavController(fragmentHostForNav.findNavController())

    }

    override fun PermissionGranted(request_code: Int) {

        Log.i("PERMISSION","GRANTED")
    }

    override fun PartialPermissionGranted(request_code: Int, granted_permissions: ArrayList<String>) {


        Log.i("PERMISSION PARTIALLY","GRANTED")
    }

    override fun PermissionDenied(request_code: Int) {

        permissionUtils!!.checkPermission(permissions,"The app needs external storage and camera permissions",1)
        Log.i("PERMISSION","DENIED")
    }

    override fun NeverAskAgain(request_code: Int) {

        Log.i("PERMISSION","NEVER ASK AGAIN");
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionUtils!!.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

}