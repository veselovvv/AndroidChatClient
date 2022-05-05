package com.veselovvv.androidchatclient.data.message

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.message.MessageDomain

sealed class MessageData : Abstract.Object<MessageDomain, MessageDataToDomainMapper> {
    class Success : MessageData() {
        override fun map(mapper: MessageDataToDomainMapper) = mapper.map()
    }

    data class Fail(private val exception: Exception) : MessageData() {
        override fun map(mapper: MessageDataToDomainMapper) = mapper.map(exception)
    }
}