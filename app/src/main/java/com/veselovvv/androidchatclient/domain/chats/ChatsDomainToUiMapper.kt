package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.chats.ChatsUi

interface ChatsDomainToUiMapper : Abstract.Mapper {
    fun map(chats: List<ChatDomain>): ChatsUi
    fun map(errorType: ErrorType): ChatsUi
}