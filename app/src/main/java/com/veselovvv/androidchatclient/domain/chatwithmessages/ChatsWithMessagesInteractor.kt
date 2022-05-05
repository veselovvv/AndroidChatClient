package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.data.chatwithmessages.ChatsWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatsWithMessagesRepository

interface ChatsWithMessagesInteractor {
    suspend fun fetchChatWithMessages(chatId: String): ChatsWithMessagesDomain
    fun getUserId(): String

    class Base(
        private val chatsWithMessagesRepository: ChatsWithMessagesRepository,
        private val mapper: ChatsWithMessagesDataToDomainMapper
    ) : ChatsWithMessagesInteractor {
        override suspend fun fetchChatWithMessages(chatId: String) =
            chatsWithMessagesRepository.fetchChatWithMessages(chatId).map(mapper)
        override fun getUserId() = chatsWithMessagesRepository.getUserId()
    }
}