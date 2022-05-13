package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.ui.chats.ChatUi

interface ChatDomainToUiMapper : Abstract.Mapper {
    fun map(
        id: String,
        title: String,
        companionId: String,
        lastMessageText: String,
        lastMessagePathToFile: String,
        photoPath: String
    ): ChatUi
}