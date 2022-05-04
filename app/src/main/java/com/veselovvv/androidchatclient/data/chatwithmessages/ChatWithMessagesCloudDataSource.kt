package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veselovvv.androidchatclient.data.chats.net.ChatService

interface ChatWithMessagesCloudDataSource {
    suspend fun fetchChatWithMessages(token: String, chatId: String): ChatWithMessages

    class Base(private val service: ChatService, private val gson: Gson) : ChatWithMessagesCloudDataSource {
        private val type = object : TypeToken<ChatWithMessages>() {}.type

        override suspend fun fetchChatWithMessages(token: String, chatId: String): ChatWithMessages =
            gson.fromJson(service.getChat(token, chatId).string(), type)
    }
}