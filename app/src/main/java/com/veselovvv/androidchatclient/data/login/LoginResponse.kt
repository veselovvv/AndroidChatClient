package com.veselovvv.androidchatclient.data.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    private val token: String,
    @SerializedName("userId")
    private val userId: String
) {
    val userToken get() = this.token
    val id get() = this.userId
}
