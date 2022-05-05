package com.veselovvv.androidchatclient.domain.message

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.message.MessagesUi

interface MessageDomainToUiMapper : Abstract.Mapper {
    fun map(): MessagesUi
    fun map(errorType: ErrorType): MessagesUi
}