package com.veselovvv.androidchatclient.data.message

import com.google.gson.annotations.SerializedName

data class MessageDTO(
    @SerializedName("text")
    private val text: String,
    @SerializedName("pathToFile")
    private val pathToFile: String,
    @SerializedName("chatId")
    private val chatId: String,
    @SerializedName("userId")
    private val userId: String
)
