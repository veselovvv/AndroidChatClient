package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.message.Message

interface ToChatWithMessagesMapper : Abstract.Mapper {
    fun map(chat: ChatDetails, messages: List<Message>): ChatWithMessagesData

    class Base : ToChatWithMessagesMapper {
        override fun map(chat: ChatDetails, messages: List<Message>) =
            ChatWithMessagesData(chat, messages.sortedBy { it.dateTime })
    }
}