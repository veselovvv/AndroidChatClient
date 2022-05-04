package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.chatdetails.ChatDetails
import com.veselovvv.androidchatclient.data.messages.Message

data class ChatWithMessages(
    @SerializedName("chat")
    private val chat: ChatDetails,
    @SerializedName("messages")
    private val messages: List<Message>
) : Abstract.Object<ChatWithMessagesData, ToChatWithMessagesMapper> {
    val chatDetails get() = this.chat.chatSettingsList

    override fun map(mapper: ToChatWithMessagesMapper) = mapper.map(chat, messages)
}