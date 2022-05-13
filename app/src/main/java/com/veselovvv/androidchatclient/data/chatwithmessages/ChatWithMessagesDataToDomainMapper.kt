package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.message.Message
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatWithMessagesDomain

interface ChatWithMessagesDataToDomainMapper : Abstract.Mapper {
    fun map(chat: ChatDetails, messages: List<Message>): ChatWithMessagesDomain
}