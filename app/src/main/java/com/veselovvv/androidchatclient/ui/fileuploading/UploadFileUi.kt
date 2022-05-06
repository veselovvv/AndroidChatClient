package com.veselovvv.androidchatclient.ui.fileuploading

import android.view.View
import com.google.android.material.snackbar.Snackbar

sealed class UploadFileUi {
    open fun map(loadFile: LoadFile) = Unit
    open fun map(view: View) = Unit

    class Success(private val filePath: String) : UploadFileUi() {
        override fun map(loadFile: LoadFile) = loadFile.load(filePath)
    }

    class Fail(private val message: String) : UploadFileUi() {
        override fun map(view: View) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
