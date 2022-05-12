package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.data.chatwithmessages.ChatsWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatsWithMessagesRepository

interface ChatsWithMessagesInteractor {
    suspend fun fetchChatWithMessages(chatId: String): ChatsWithMessagesDomain
    suspend fun createChat(title: String, createdBy: String, userIds: List<String>): ChatsWithMessagesDomain
    suspend fun addMember(groupId: String, userId: String, isChatAdmin: String): ChatsWithMessagesDomain
    suspend fun editChatSettings(
        chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean
    ): ChatsWithMessagesDomain
    suspend fun leaveGroupChat(groupId: String, userId: String): ChatsWithMessagesDomain
    suspend fun deleteChat(chatId: String): ChatsWithMessagesDomain
    fun getUserToken(): String
    fun getUserId(): String

    class Base(
        private val chatsWithMessagesRepository: ChatsWithMessagesRepository,
        private val mapper: ChatsWithMessagesDataToDomainMapper
    ) : ChatsWithMessagesInteractor {
        override suspend fun fetchChatWithMessages(chatId: String) =
            chatsWithMessagesRepository.fetchChatWithMessages(chatId).map(mapper)

        override suspend fun createChat(title: String, createdBy: String, userIds: List<String>) =
            chatsWithMessagesRepository.createChat(title, createdBy, userIds).map(mapper)

        override suspend fun addMember(groupId: String, userId: String, isChatAdmin: String) =
            chatsWithMessagesRepository.addMember(groupId, userId, isChatAdmin).map(mapper)

        override suspend fun editChatSettings(
            chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean
        ) = chatsWithMessagesRepository.editChatSettings(chatId, userId, banned, sendNotifications).map(mapper)

        override suspend fun leaveGroupChat(groupId: String, userId: String) =
            chatsWithMessagesRepository.leaveGroupChat(groupId, userId).map(mapper)

        override suspend fun deleteChat(chatId: String) =
            chatsWithMessagesRepository.deleteChat(chatId).map(mapper)

        override fun getUserToken() = chatsWithMessagesRepository.getUserToken()
        override fun getUserId() = chatsWithMessagesRepository.getUserId()
    }
}