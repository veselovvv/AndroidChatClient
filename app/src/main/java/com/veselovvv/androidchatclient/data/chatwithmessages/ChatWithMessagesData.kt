package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.message.Message
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatWithMessagesDomain

class ChatWithMessagesData(
    private val chat: ChatDetails,
    private val messages: List<Message>
) : Abstract.Object<ChatWithMessagesDomain, ChatWithMessagesDataToDomainMapper> {
    override fun map(mapper: ChatWithMessagesDataToDomainMapper) = mapper.map(chat, messages)
}