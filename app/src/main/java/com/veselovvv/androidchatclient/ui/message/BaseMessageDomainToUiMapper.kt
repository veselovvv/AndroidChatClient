package com.veselovvv.androidchatclient.ui.message

import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.message.MessageDomainToUiMapper

class BaseMessageDomainToUiMapper(private val resourceProvider: ResourceProvider) : MessageDomainToUiMapper {
    override fun map() = MessagesUi.Success()
    override fun map(errorType: ErrorType) = MessagesUi.Fail(errorType, resourceProvider)
}