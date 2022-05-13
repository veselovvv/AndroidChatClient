package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.data.chatwithmessages.ChatDetails
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.data.message.Message

class BaseChatWithMessagesDataToDomainMapper : ChatWithMessagesDataToDomainMapper {
    override fun map(chat: ChatDetails, messages: List<Message>) =
        ChatWithMessagesDomain(chat, messages)
}