package com.veselovvv.androidchatclient.data.message

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.message.MessageDomain

interface MessageDataToDomainMapper : Abstract.Mapper {
    fun map(): MessageDomain
    fun map(exception: Exception): MessageDomain
}