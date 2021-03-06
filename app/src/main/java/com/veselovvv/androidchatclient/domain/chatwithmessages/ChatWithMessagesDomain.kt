package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatDetails
import com.veselovvv.androidchatclient.data.message.Message
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatWithMessagesUi

class ChatWithMessagesDomain(
    private val chat: ChatDetails,
    private val messages: List<Message>
) : Abstract.Object<ChatWithMessagesUi, ChatWithMessagesDomainToUiMapper> {
    override fun map(mapper: ChatWithMessagesDomainToUiMapper) = mapper.map(chat, messages)
}