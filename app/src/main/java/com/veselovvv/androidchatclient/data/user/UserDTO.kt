package com.veselovvv.androidchatclient.data.user

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("name")
    private val name: String,
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String,
    @SerializedName("roleId")
    private val roleId: String,
    @SerializedName("photoPathToFile")
    private val photoPathToFile: String
)
