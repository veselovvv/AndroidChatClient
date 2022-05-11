package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.annotations.SerializedName

data class CreateChatDto(
    @SerializedName("title")
    private val title: String,
    @SerializedName("createdBy")
    private val createdBy: String,
    @SerializedName("userIds")
    private val userIds: List<String>
)
