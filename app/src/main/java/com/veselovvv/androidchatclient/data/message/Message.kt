package com.veselovvv.androidchatclient.data.message

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.data.user.User

data class Message(
    @SerializedName("id")
    private val id: String,
    @SerializedName("text")
    private val text: String,
    @SerializedName("pathToFile")
    private val pathToFile: String?,
    @SerializedName("chatId")
    private val chatId: String,
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