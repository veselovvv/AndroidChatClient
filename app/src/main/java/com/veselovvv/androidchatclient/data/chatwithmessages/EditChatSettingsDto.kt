package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.annotations.SerializedName

data class EditChatSettingsDto(
    @SerializedName("banned")
    private val banned: Boolean,
    @SerializedName("sendNotifications")
    private val sendNotifications: Boolean
)
