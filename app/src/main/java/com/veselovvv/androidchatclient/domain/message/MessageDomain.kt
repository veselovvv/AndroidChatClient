package com.veselovvv.androidchatclient.domain.message

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.message.MessagesUi
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class MessageDomain : Abstract.Object<MessagesUi, MessageDomainToUiMapper> {
    class Success : MessageDomain() {
        override fun map(mapper: MessageDomainToUiMapper) = mapper.map()
    }

    class Fail(private val exception: Exception): MessageDomain() {
        override fun map(mapper: MessageDomainToUiMapper) = mapper.map(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
