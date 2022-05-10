package com.veselovvv.androidchatclient.data.user

import com.google.gson.annotations.SerializedName

data class EditUserDTO(
    @SerializedName("name")
    private val name: String,
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String,
    @SerializedName("photoPathToFile")
    private val photoPathToFile: String
)