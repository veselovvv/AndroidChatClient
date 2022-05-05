package com.veselovvv.androidchatclient.data.chatdetails

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.data.user.net.UserChatSettings

data class ChatDetails(
    /*@SerializedName("user")
    private val user: User?,*/
    @SerializedName("userChatSettingsList")
    private val userChatSettingsList: List<UserChatSettings>
) {
    val chatSettingsList get() = this.userChatSettingsList
}