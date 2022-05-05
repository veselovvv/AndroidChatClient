package com.veselovvv.androidchatclient.data.user.net

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserChatSettingsCompositeId(
    @SerializedName("chatId")
    private val chatId: UUID,
    @SerializedName("userId")
    private val userId: UUID
) {
    val idUser get() = this.userId
}
