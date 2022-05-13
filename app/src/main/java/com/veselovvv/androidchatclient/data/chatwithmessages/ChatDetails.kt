package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.data.user.User
import com.veselovvv.androidchatclient.data.user.UserChatSettings

data class ChatDetails(
    @SerializedName("user")
    private val user: User?,
    @SerializedName("userChatSettingsList")
    private val userChatSettingsList: List<UserChatSettings>
) {
    val createdByUserId get() = this.user?.userId
    val chatSettingsList get() = this.userChatSettingsList
}