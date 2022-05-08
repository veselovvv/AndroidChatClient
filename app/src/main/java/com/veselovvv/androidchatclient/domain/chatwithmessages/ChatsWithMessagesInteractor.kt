package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.data.chatwithmessages.ChatsWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatsWithMessagesRepository
import com.veselovvv.androidchatclient.data.chatwithmessages.EditChatSettingsDto

interface ChatsWithMessagesInteractor {
    suspend fun fetchChatWithMessages(chatId: String): ChatsWithMessagesDomain
    suspend fun editChatSettings(
        chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean
    ): ChatsWithMessagesDomain
    suspend fun leaveGroupChat(groupId: String, userId: String): ChatsWithMessagesDomain
    fun getUserToken(): String
    fun getUserId(): String

    class Base(
        private val chatsWithMessagesRepository: ChatsWithMessagesRepository,
        private val mapper: ChatsWithMessagesDataToDomainMapper
    ) : ChatsWithMessagesInteractor {
        override suspend fun fetchChatWithMessages(chatId: String) =
            chatsWithMessagesRepository.fetchChatWithMessages(chatId).map(mapper)

        override suspend fun editChatSettings(
            chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean
        ) = chatsWithMessagesRepository.editChatSettings(chatId, userId, banned, sendNotifications).map(mapper)

        override suspend fun leaveGroupChat(groupId: String, userId: String) =
            chatsWithMessagesRepository.leaveGroupChat(groupId, userId).map(mapper)

        override fun getUserToken() = chatsWithMessagesRepository.getUserToken()
        override fun getUserId() = chatsWithMessagesRepository.getUserId()
    }
}