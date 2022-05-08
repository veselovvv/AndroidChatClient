package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.data.user.SessionManager

interface ChatsWithMessagesRepository {
    suspend fun fetchChatWithMessages(chatId: String): ChatsWithMessagesData
    suspend fun editChatSettings(
        chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean
    ): ChatsWithMessagesData
    suspend fun leaveGroupChat(groupId: String, userId: String): ChatsWithMessagesData
    fun getUserToken(): String
    fun getUserId(): String

    class Base(
        private val cloudDataSource: ChatWithMessagesCloudDataSource,
        private val chatWithMessagesCloudMapper: ChatWithMessagesCloudMapper,
        private val toEditChatSettingsDtoMapper: ToEditChatSettingsDtoMapper,
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

        override suspend fun editChatSettings(
            chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean
        ) = try {
            val token = sessionManager.read().first
            val editChatSettingsDto = toEditChatSettingsDtoMapper.map(banned, sendNotifications)
            val chatWithMessagesCloud =
                cloudDataSource.editChatSettings(token, chatId, userId, editChatSettingsDto)
            val chatWithMessages = chatWithMessagesCloudMapper.map(chatWithMessagesCloud)
            ChatsWithMessagesData.Success(chatWithMessages)
        } catch (exception: Exception) {
            ChatsWithMessagesData.Fail(exception)
        }

        override suspend fun leaveGroupChat(groupId: String, userId: String) = try {
            val token = sessionManager.read().first
            cloudDataSource.leaveGroupChat(token, groupId, userId)
            ChatsWithMessagesData.Empty()
        } catch (exception: Exception) {
            ChatsWithMessagesData.Fail(exception)
        }

        override fun getUserToken() = sessionManager.read().first
        override fun getUserId() = sessionManager.read().second
    }
}