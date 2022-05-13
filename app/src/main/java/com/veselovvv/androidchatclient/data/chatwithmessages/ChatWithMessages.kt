package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.message.Message

data class ChatWithMessages(
    @SerializedName("chat")
    private val chat: ChatDetails,
    @SerializedName("messages")
    private val messages: List<Message>
) : Abstract.Object<ChatWithMessagesData, ToChatWithMessagesMapper> {
    val chatDetails get() = this.chat.chatSettingsList
    val lastMessage get() =
        if (this.messages.isNotEmpty()) this.messages.sortedBy { it.dateTime }.last() else null

    override fun map(mapper: ToChatWithMessagesMapper) = mapper.map(chat, messages)
}