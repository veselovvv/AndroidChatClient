package com.veselovvv.androidchatclient.data.message

import com.veselovvv.androidchatclient.data.chats.net.ChatService

interface MessageCloudDataSource {
    suspend fun sendDirectMessage(token: String, message: MessageDTO, userId: String)
    suspend fun sendGroupMessage(token: String, message: MessageDTO, groupId: String)

    class Base(private val service: ChatService) : MessageCloudDataSource {
        override suspend fun sendDirectMessage(token: String, message: MessageDTO, userId: String) =
            service.sendDirectMessage(token, message, userId)

        override suspend fun sendGroupMessage(token: String, message: MessageDTO, groupId: String) =
            service.sendGroupMessage(token, message, groupId)

    }
}