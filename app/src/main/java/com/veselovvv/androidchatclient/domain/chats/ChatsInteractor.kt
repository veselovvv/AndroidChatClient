package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.data.chats.ChatsDataToDomainMapper
import com.veselovvv.androidchatclient.data.chats.ChatsRepository

interface ChatsInteractor {
    suspend fun fetchChats(): ChatsDomain
    suspend fun searchChats(query: String): ChatsDomain

    class Base(
        private val chatsRepository: ChatsRepository,
        private val mapper: ChatsDataToDomainMapper
    ) : ChatsInteractor {
        override suspend fun fetchChats() =
            chatsRepository.fetchChats().map(mapper)

        override suspend fun searchChats(query: String) =
            chatsRepository.searchChats(query).map(mapper)
    }
}