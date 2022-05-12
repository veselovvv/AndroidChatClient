package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.annotations.SerializedName

data class AddMemberDto(
    @SerializedName("userId")
    private val userId: String,
    @SerializedName("isChatAdmin")
    private val isChatAdmin: String
)
