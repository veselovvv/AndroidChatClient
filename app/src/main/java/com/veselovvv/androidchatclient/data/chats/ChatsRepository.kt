package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.data.chatwithmessages.ChatWithMessagesCloudDataSource
import com.veselovvv.androidchatclient.data.user.SessionManager
import com.veselovvv.androidchatclient.data.user.UserCloudDataSource

interface ChatsRepository {
    suspend fun fetchChats(): ChatsData
    suspend fun searchChats(query: String): ChatsData

    class Base(
        private val cloudDataSource: ChatsCloudDataSource,
        private val chatsWithMessagesCloudDataSource: ChatWithMessagesCloudDataSource,
        private val userCloudDataSource: UserCloudDataSource,
        private val chatsCloudMapper: ChatsCloudMapper,
        private val sessionManager: SessionManager
    ) : ChatsRepository {
        override suspend fun fetchChats() = getChats(null)
        override suspend fun searchChats(query: String) = getChats(query)

        private suspend fun getChats(query: String?) = try {
            val token = sessionManager.read().first
            val userId = sessionManager.read().second
            var chatsCloudList = cloudDataSource.fetchChats(token, userId)
            chatsCloudList.forEach { chat ->
                if (chat.chatTitle == "") {
                    val chatWithMessages = chatsWithMessagesCloudDataSource.fetchChatWithMessages(
                        token,
                        chat.chatId.toString()
                    )
                    chatWithMessages.chatDetails.forEach {
                        if (!it.userId.equals(userId))
                            chat.chatTitle = userCloudDataSource.fetchUser(token, it.userId.toString()).userName
                    }
                }
            }
            if (query != null) {
                chatsCloudList = chatsCloudList.filter { chatCloud ->
                    chatCloud.chatTitle.startsWith(query)
                }
            }
            val chats = chatsCloudMapper.map(chatsCloudList)
            ChatsData.Success(chats)
        } catch (exception: Exception) {
            ChatsData.Fail(exception)
        }
    }
}