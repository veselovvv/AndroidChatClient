package com.veselovvv.androidchatclient.data.user

import com.google.gson.annotations.SerializedName

data class UserChatSettingsCompositeId(
    @SerializedName("chatId")
    private val chatId: String,
    @SerializedName("userId")
    private val userId: String
) {
    val idUser get() = this.userId
}
