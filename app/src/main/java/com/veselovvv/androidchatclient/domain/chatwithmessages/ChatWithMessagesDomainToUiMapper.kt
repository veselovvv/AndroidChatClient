package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.chatdetails.ChatDetails
import com.veselovvv.androidchatclient.data.messages.Message
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatWithMessagesUi

interface ChatWithMessagesDomainToUiMapper : Abstract.Mapper {
    fun map(chat: ChatDetails, messages: List<Message>): ChatWithMessagesUi
}