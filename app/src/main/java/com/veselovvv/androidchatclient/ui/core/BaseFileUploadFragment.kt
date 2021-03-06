package com.veselovvv.androidchatclient.ui.core

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

abstract class BaseFileUploadFragment(@LayoutRes private val layout: Int) : BaseFragment(layout) {
    private companion object {
        const val READ_EXTERNAL_REQUEST = 1
        const val FILE_REQUEST_CODE = 100
    }

    abstract fun doOnActivityResult(data: Uri)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data?.data != null)
            doOnActivityResult(data.data!!)
    }

    protected fun getPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_REQUEST)
        else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "*/*"
            startActivityForResult(intent, FILE_REQUEST_CODE)
        }
    }
}