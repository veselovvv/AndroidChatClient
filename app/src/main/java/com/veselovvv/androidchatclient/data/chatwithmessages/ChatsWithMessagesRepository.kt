package com.veselovvv.androidchatclient.data.chatwithmessages

import android.util.Log
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
            //TODO
            /*val companionId = chatWithMessagesCloud.companionId
            Log.d("LogTag", companionId)*/
            val chatWithMessages = chatWithMessagesCloudMapper.map(chatWithMessagesCloud)
            ChatsWithMessagesData.Success(chatWithMessages)
        } catch (exception: Exception) {
            ChatsWithMessagesData.Fail(exception)
        }

        override fun getUserId() = sessionManager.read().second
    }
}