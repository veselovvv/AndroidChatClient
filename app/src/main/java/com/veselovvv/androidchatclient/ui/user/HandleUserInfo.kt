package com.veselovvv.androidchatclient.ui.user

interface HandleUserInfo {
    fun handle(id: String, name: String, email: String, password: String, photoPathToFile: String)
}
