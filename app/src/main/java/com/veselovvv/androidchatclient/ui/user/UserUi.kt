package com.veselovvv.androidchatclient.ui.user

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.ui.login.Navigate

sealed class UserUi {
    open fun map(view: View, navigate: Navigate) = Unit
    open fun map(handleUserInfo: HandleUserInfo) = Unit
    open fun map(view: View) = Unit

    class RegisterSuccess : UserUi() {
        override fun map(view: View, navigate: Navigate) {
            Snackbar.make(view, view.context.getString(R.string.user_created), Snackbar.LENGTH_SHORT).show()
            navigate.navigate()
        }
    }

    class Base(
        private val id: String,
        private val name: String,
        private val email: String,
        private val password: String,
        private val photoPathToFile: String
    ) : UserUi() {
        override fun map(handleUserInfo: HandleUserInfo) =
            handleUserInfo.handle(id, name, email, password, photoPathToFile)
    }

    class Fail(private val message: String) : UserUi() {
        override fun map(view: View) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
