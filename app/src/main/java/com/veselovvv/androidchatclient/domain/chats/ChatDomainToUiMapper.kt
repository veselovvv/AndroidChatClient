package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.ui.chats.ChatUi
import java.util.*

interface ChatDomainToUiMapper : Abstract.Mapper {
    fun map(id: UUID, title: String, companionId: String, lastMessageText: String): ChatUi
}