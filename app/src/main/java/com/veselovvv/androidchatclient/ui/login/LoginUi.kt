package com.veselovvv.androidchatclient.ui.login

import com.google.android.material.textview.MaterialTextView

sealed class LoginUi {
    open fun map(navigate: Navigate) = Unit
    open fun map(messageTextView: MaterialTextView) = Unit

    class Success : LoginUi() {
        override fun map(navigate: Navigate) = navigate.navigate()
    }

    class Fail(private val message: String) : LoginUi() {
        override fun map(messageTextView: MaterialTextView) {
            messageTextView.text = message
        }
    }
}