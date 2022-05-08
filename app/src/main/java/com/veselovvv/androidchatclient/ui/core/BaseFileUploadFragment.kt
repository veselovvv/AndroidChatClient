package com.veselovvv.androidchatclient.ui.core

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

abstract class BaseFileUploadFragment : Fragment() {
    protected companion object {
        const val READ_EXTERNAL_REQUEST = 1
        const val FILE_REQUEST_CODE = 100
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