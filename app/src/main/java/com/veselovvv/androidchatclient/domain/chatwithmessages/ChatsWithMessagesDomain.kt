package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatWithMessagesData
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatsWithMessagesUi
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class ChatsWithMessagesDomain : Abstract.Object<ChatsWithMessagesUi, ChatsWithMessagesDomainToUiMapper> {
    class Empty : ChatsWithMessagesDomain() {
        override fun map(mapper: ChatsWithMessagesDomainToUiMapper) =
            mapper.map()
    }

    class Success(
        private val chatWithMessages: ChatWithMessagesData,
        private val chatWithMessagesMapper: ChatWithMessagesDataToDomainMapper
    ) : ChatsWithMessagesDomain() {
        override fun map(mapper: ChatsWithMessagesDomainToUiMapper) =
            mapper.map(chatWithMessages.map(chatWithMessagesMapper))
    }

    class Fail(private val exception: Exception) : ChatsWithMessagesDomain() {
        override fun map(mapper: ChatsWithMessagesDomainToUiMapper) = mapper.map(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}