package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.data.user.SessionManager

interface ChatsWithMessagesRepository {
    suspend fun fetchChatWithMessages(chatId: String): ChatsWithMessagesData
    fun getUserId(): String

    class Base(
        private val cloudDataSource: ChatWithMessagesCloudDataSource,
        private val chatWithMessagesCloudMapper: ChatWithMessagesCloudMapper,
        private val sessionManager: SessionManager,
    ) : ChatsWithMessagesRepository {
        override suspend fun fetchChatWithMessages(chatId: String) = try {
            val token = sessionManager.read().first
            val chatWithMessagesCloud = cloudDataSource.fetchChatWithMessages(token, chatId)
            val chatWithMessages = chatWithMessagesCloudMapper.map(chatWithMessagesCloud)
            ChatsWithMessagesData.Success(chatWithMessages)
        } catch (exception: Exception) {
            ChatsWithMessagesData.Fail(exception)
        }

        override fun getUserId() = sessionManager.read().second
    }
}