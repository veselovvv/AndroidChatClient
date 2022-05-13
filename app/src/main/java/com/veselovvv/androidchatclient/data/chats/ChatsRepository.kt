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
        private val sessionManager: SessionManager,
    ) : ChatsRepository {
        override suspend fun fetchChats() = getChats(null)
        override suspend fun searchChats(query: String) = getChats(query)

        private suspend fun getChats(query: String?) = try {
            val token = sessionManager.read().first
            val userId = sessionManager.read().second
            var chatsCloudList = cloudDataSource.fetchChats(token, userId)
            chatsCloudList.forEach { chat ->
                val chatWithMessages = chatsWithMessagesCloudDataSource.fetchChatWithMessages(
                    token,
                    chat.chatId
                )
                chat.lastChatMessage = chatWithMessages.lastMessage

                if (chat.chatTitle == "") {
                    chatWithMessages.chatDetails.forEach {
                        if (it.userId.toString() != userId) {
                            val user = userCloudDataSource.fetchUser(token, it.userId.toString())
                            chat.chatTitle = user.userName
                            chat.photoPath = user.photoPath
                            chat.userCompanionId = it.userId.toString()
                        }
                    }
                }
            }
            chatsCloudList = chatsCloudList.sortedByDescending { it.lastChatMessage?.dateTime }

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