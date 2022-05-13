package com.veselovvv.androidchatclient.data.messages

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.data.user.net.User
import java.util.*

data class Message(
    @SerializedName("id")
    private val id: UUID,
    @SerializedName("text")
    private val text: String,
    @SerializedName("pathToFile")
    private val pathToFile: String?,
    @SerializedName("chatId")
    private val chatId: UUID,
    @SerializedName("user")
    private val user: User,
    @SerializedName("messageDateTime")
    private val messageDateTime: String
) {
    val messageText get() = this.text
    val messagePathToFile get() = this.pathToFile
    val sender get() = this.user
    val dateTime get() = this.messageDateTime
}