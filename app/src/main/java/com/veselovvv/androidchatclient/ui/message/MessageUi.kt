package com.veselovvv.androidchatclient.ui.message

import android.view.View
import com.google.android.material.snackbar.Snackbar

sealed class MessageUi {
    open fun map() = Unit
    open fun map(view: View) = Unit

    class Success : MessageUi() {
        override fun map() = Unit //TODO?
    }

    class Fail(private val message: String) : MessageUi() {
        override fun map(view: View) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
