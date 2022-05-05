package com.veselovvv.androidchatclient.domain.message

import com.veselovvv.androidchatclient.data.message.MessageDataToDomainMapper

class BaseMessageDataToDomainMapper : MessageDataToDomainMapper {
    override fun map() = MessageDomain.Success()
    override fun map(exception: Exception) = MessageDomain.Fail(exception)
}