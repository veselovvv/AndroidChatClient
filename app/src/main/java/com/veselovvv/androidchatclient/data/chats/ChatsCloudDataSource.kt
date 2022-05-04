package com.veselovvv.androidchatclient.data.chats

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veselovvv.androidchatclient.data.chats.net.Chat
import com.veselovvv.androidchatclient.data.users.net.UserService
import java.util.*

interface ChatsCloudDataSource {
    suspend fun fetchChats(token: String, userId: String): List<Chat>

    class Base(private val service: UserService, private val gson: Gson) : ChatsCloudDataSource {
        private val type = object : TypeToken<List<Chat>>() {}.type

        override suspend fun fetchChats(token: String, userId: String): List<Chat> =
            gson.fromJson(service.getChats(token, userId).string(), type)
    }
}