package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veselovvv.androidchatclient.data.chats.net.ChatService

interface ChatWithMessagesCloudDataSource {
    suspend fun fetchChatWithMessages(token: String, chatId: String): ChatWithMessages
    suspend fun editChatSettings(
        token: String, chatId: String, userId: String, editChatSettings: EditChatSettingsDto
    ): ChatWithMessages

    class Base(private val service: ChatService, private val gson: Gson) : ChatWithMessagesCloudDataSource {
        private val type = object : TypeToken<ChatWithMessages>() {}.type

        override suspend fun fetchChatWithMessages(token: String, chatId: String): ChatWithMessages =
            gson.fromJson(service.getChat(token, chatId).string(), type)

        override suspend fun editChatSettings(
            token: String, chatId: String, userId: String, editChatSettings: EditChatSettingsDto
        ): ChatWithMessages = gson.fromJson(
            service.editChatSettings(token, chatId, userId, editChatSettings).string(), type
        )
    }
}