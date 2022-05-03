package com.veselovvv.androidchatclient.ui.login

import android.util.Patterns

interface Validator {
    fun isUsernameCorrect(username: String?): Boolean
    fun isEmailCorrect(email: String?): Boolean
    fun isPasswordCorrect(password: String?): Boolean

    class Base : Validator {
        override fun isUsernameCorrect(username: String?) =
            if (username != null) username.length > 2 else false

        override fun isEmailCorrect(email: String?) =
            Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()

        override fun isPasswordCorrect(password: String?) =
            if (password != null) password.length > 7 else false
    }
}