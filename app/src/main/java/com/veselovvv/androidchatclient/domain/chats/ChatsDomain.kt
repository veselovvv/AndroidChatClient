package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.data.chats.ChatData
import com.veselovvv.androidchatclient.data.chats.ChatDataToDomainMapper
import com.veselovvv.androidchatclient.ui.chats.ChatsUi
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

sealed class ChatsDomain : Abstract.Object<ChatsUi, ChatsDomainToUiMapper> {
    class Success(
        private val chats: List<ChatData>,
        private val chatMapper: ChatDataToDomainMapper
    ) : ChatsDomain() {
        override fun map(mapper: ChatsDomainToUiMapper) = mapper.map(chats.map { it.map(chatMapper) })
    }

    class Fail(private val exception: Exception): ChatsDomain() {
        override fun map(mapper: ChatsDomainToUiMapper) = mapper.map(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
