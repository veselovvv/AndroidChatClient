package com.veselovvv.androidchatclient.domain.fileuploading

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface FileProvider {
    fun getFile(fileUri: Uri): MultipartBody.Part
    fun getPathFromUri(contentUri: Uri): String?

    class Base(private val context: Context) : FileProvider {
        override fun getFile(fileUri: Uri): MultipartBody.Part {
            val file = File(getPathFromUri(fileUri) ?: "") //TODO
            return MultipartBody.Part.createFormData(
                "file", file.name, RequestBody.create("*/*".toMediaTypeOrNull(), file)
            )
        }

        override fun getPathFromUri(contentUri: Uri): String? {
            var cursor: Cursor? = null
            return try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri, proj, null, null, null)
                val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (columnIndex != null) {
                    cursor?.moveToFirst()
                    cursor?.getString(columnIndex)
                } else "" //TODO
            } finally {
                cursor?.close()
            }
        }
    }
}