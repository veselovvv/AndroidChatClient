package com.veselovvv.androidchatclient.domain.message

import com.veselovvv.androidchatclient.data.message.MessageDataToDomainMapper
import com.veselovvv.androidchatclient.data.message.MessageRepository

interface MessageInteractor {
    suspend fun sendDirectMessage(
        text: String, pathToFile: String, chatId: String, userId: String, pathUserId: String
    ): MessageDomain

    suspend fun sendGroupMessage(
        text: String, pathToFile: String, chatId: String, userId: String, pathGroupId: String
    ): MessageDomain

    class Base(
        private val messageRepository: MessageRepository,
        private val mapper: MessageDataToDomainMapper
    ) : MessageInteractor {
        override suspend fun sendDirectMessage(
            text: String, pathToFile: String, chatId: String, userId: String, pathUserId: String
        ) = messageRepository.sendDirectMessage(text, pathToFile, chatId, userId, pathUserId).map(mapper)

        override suspend fun sendGroupMessage(
            text: String, pathToFile: String, chatId: String, userId: String, pathGroupId: String
        ) = messageRepository.sendGroupMessage(text, pathToFile, chatId, userId, pathGroupId).map(mapper)
    }
}