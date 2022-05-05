package com.veselovvv.androidchatclient.data.user.net

import com.google.gson.annotations.SerializedName

data class UserChatSettings(
    @SerializedName("id")
    private val id: UserChatSettingsCompositeId,
    @SerializedName("sendNotifications")
    private val sendNotifications: Boolean,
    @SerializedName("banned")
    private val banned: Boolean,
    @SerializedName("isChatAdmin")
    private val isChatAdmin: Boolean
) {
    val userId get() = this.id.idUser
}
