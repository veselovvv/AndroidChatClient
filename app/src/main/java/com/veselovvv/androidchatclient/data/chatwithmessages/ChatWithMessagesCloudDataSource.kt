package com.veselovvv.androidchatclient.data.chatwithmessages

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veselovvv.androidchatclient.data.chats.ChatService

interface ChatWithMessagesCloudDataSource {
    suspend fun fetchChatWithMessages(token: String, chatId: String): ChatWithMessages
    suspend fun createChat(token: String, createChatDto: CreateChatDto)
    suspend fun addMember(token: String, groupId: String, addMemberDto: AddMemberDto)
    suspend fun editChatSettings(
        token: String, chatId: String, userId: String, editChatSettings: EditChatSettingsDto
    ): ChatWithMessages
    suspend fun leaveGroupChat(token: String, groupId: String, userId: String)
    suspend fun deleteChat(token: String, chatId: String)

    class Base(private val service: ChatService, private val gson: Gson) : ChatWithMessagesCloudDataSource {
        private val type = object : TypeToken<ChatWithMessages>() {}.type

        override suspend fun fetchChatWithMessages(token: String, chatId: String): ChatWithMessages =
            gson.fromJson(service.getChat(token, chatId).string(), type)

        override suspend fun createChat(token: String, createChatDto: CreateChatDto) =
            service.createChat(token, createChatDto)

        override suspend fun addMember(token: String, groupId: String, addMemberDto: AddMemberDto) {
            service.addMember(token, groupId, addMemberDto)
        }

        override suspend fun editChatSettings(
            token: String, chatId: String, userId: String, editChatSettings: EditChatSettingsDto
        ): ChatWithMessages = gson.fromJson(
            service.editChatSettings(token, chatId, userId, editChatSettings).string(), type
        )

        override suspend fun leaveGroupChat(token: String, groupId: String, userId: String) =
            service.leaveGroupChat(token, groupId, userId)

        override suspend fun deleteChat(token: String, chatId: String) = service.deleteChat(token, chatId)
    }
}