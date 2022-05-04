package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomain

interface ChatsWithMessagesDataToDomainMapper : Abstract.Mapper {
    fun map(chatWithMessages: ChatWithMessagesData): ChatsWithMessagesDomain
    fun map(exception: Exception): ChatsWithMessagesDomain
}