package com.veselovvv.androidchatclient.data.login

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String
)
