package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.data.user.SessionManager

interface ChatsWithMessagesRepository {
    suspend fun fetchChatWithMessages(chatId: String): ChatsWithMessagesData
    suspend fun createChat(title: String, createdBy: String, userIds: List<String>): ChatsWithMessagesData
    suspend fun addMember(groupId: String, userId: String, isChatAdmin: String): ChatsWithMessagesData
    suspend fun editChatSettings(
        chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean
    ): ChatsWithMessagesData
    suspend fun leaveGroupChat(groupId: String, userId: String): ChatsWithMessagesData
    suspend fun deleteChat(chatId: String): ChatsWithMessagesData
    fun getUserToken(): String
    fun getUserId(): String

    class Base(
        private val cloudDataSource: ChatWithMessagesCloudDataSource,
        private val chatWithMessagesCloudMapper: ChatWithMessagesCloudMapper,
        private val toEditChatSettingsDtoMapper: ToEditChatSettingsDtoMapper,
        private val toCreateChatDtoMapper: ToCreateChatDtoMapper,
        private val toAddMemberDtoMapper: ToAddMemberDtoMapper,
        private val sessionManager: SessionManager
    ) : ChatsWithMessagesRepository {
        override suspend fun fetchChatWithMessages(chatId: String) = try {
            val token = sessionManager.read().first
            val chatWithMessagesCloud = cloudDataSource.fetchChatWithMessages(token, chatId)
            val chatWithMessages = chatWithMessagesCloudMapper.map(chatWithMessagesCloud)
            ChatsWithMessagesData.Success(chatWithMessages)
        } catch (exception: Exception) {
            ChatsWithMessagesData.Fail(exception)
        }

        override suspend fun createChat(title: String, createdBy: String, userIds: List<String>) = try {
            val token = sessionManager.read().first
            val createChatDTO = toCreateChatDtoMapper.map(title, createdBy, userIds)
            cloudDataSource.createChat(token, createChatDTO)
            ChatsWithMessagesData.Empty()
        } catch (exception: Exception) {
            ChatsWithMessagesData.Fail(exception)
        }

        override suspend fun addMember(groupId: String, userId: String, isChatAdmin: String) = try {
            val token = sessionManager.read().first
            val addMemberDto = toAddMemberDtoMapper.map(userId, isChatAdmin)
            cloudDataSource.addMember(token, groupId, addMemberDto)
            ChatsWithMessagesData.Empty()
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

        override suspend fun deleteChat(chatId: String) = try {
            val token = sessionManager.read().first
            cloudDataSource.deleteChat(token, chatId)
            ChatsWithMessagesData.Empty()
        } catch (exception: Exception) {
            ChatsWithMessagesData.Fail(exception)
        }

        override fun getUserToken() = sessionManager.read().first
        override fun getUserId() = sessionManager.read().second
    }
}