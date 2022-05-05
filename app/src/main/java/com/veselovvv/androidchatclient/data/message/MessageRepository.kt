package com.veselovvv.androidchatclient.data.message

import com.veselovvv.androidchatclient.data.user.SessionManager

interface MessageRepository {
    suspend fun sendDirectMessage(
        text: String, pathToFile: String, chatId: String, userId: String, pathUserId: String
    ): MessageData

    suspend fun sendGroupMessage(
        text: String, pathToFile: String, chatId: String, userId: String, pathGroupId: String
    ): MessageData

    class Base(
        private val cloudDataSource: MessageCloudDataSource,
        private val mapper: ToMessageDTOMapper,
        private val sessionManager: SessionManager
    ) : MessageRepository {
        override suspend fun sendDirectMessage(
            text: String, pathToFile: String, chatId: String, userId: String, pathUserId: String
        ) = try {
            val token = sessionManager.read().first
            val messageDTO = mapper.map(text, pathToFile, chatId, userId)
            cloudDataSource.sendDirectMessage(token, messageDTO, pathUserId)
            MessageData.Success()
        } catch (exception: Exception) {
            MessageData.Fail(exception)
        }

        override suspend fun sendGroupMessage(
            text: String, pathToFile: String, chatId: String, userId: String, pathGroupId: String
        ) = try {
            val token = sessionManager.read().first
            val messageDTO = mapper.map(text, pathToFile, chatId, userId)
            cloudDataSource.sendGroupMessage(token, messageDTO, pathGroupId)
            MessageData.Success()
        } catch (exception: Exception) {
            MessageData.Fail(exception)
        }
    }
}