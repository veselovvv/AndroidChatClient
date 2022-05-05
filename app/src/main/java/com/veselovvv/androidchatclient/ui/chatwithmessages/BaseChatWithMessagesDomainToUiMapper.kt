package com.veselovvv.androidchatclient.ui.chatwithmessages

import com.veselovvv.androidchatclient.data.chatdetails.ChatDetails
import com.veselovvv.androidchatclient.data.messages.Message
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatWithMessagesDomainToUiMapper

class BaseChatWithMessagesDomainToUiMapper : ChatWithMessagesDomainToUiMapper {
    override fun map(chat: ChatDetails, messages: List<Message>) =
        ChatWithMessagesUi.Base(chat, messages)
}