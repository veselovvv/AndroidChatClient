package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatsWithMessagesUi

interface ChatsWithMessagesDomainToUiMapper : Abstract.Mapper {
    fun map(chatWithMessages: ChatWithMessagesDomain): ChatsWithMessagesUi
    fun map(errorType: ErrorType): ChatsWithMessagesUi
}