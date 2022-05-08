package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomain

sealed class ChatsWithMessagesData : Abstract.Object<ChatsWithMessagesDomain, ChatsWithMessagesDataToDomainMapper> {
    class Empty : ChatsWithMessagesData() {
        override fun map(mapper: ChatsWithMessagesDataToDomainMapper) = mapper.map()
    }

    data class Success(private val chatWithMessages: ChatWithMessagesData) : ChatsWithMessagesData() {
        override fun map(mapper: ChatsWithMessagesDataToDomainMapper) = mapper.map(chatWithMessages)
    }

    data class Fail(private val exception: Exception) : ChatsWithMessagesData() {
        override fun map(mapper: ChatsWithMessagesDataToDomainMapper) = mapper.map(exception)
    }
}
