package com.veselovvv.androidchatclient.ui.chats

import com.veselovvv.androidchatclient.domain.chats.ChatDomainToUiMapper
import java.util.*

class BaseChatDomainToUiMapper : ChatDomainToUiMapper {
    override fun map(
        id: UUID, title: String, companionId: String, lastMessageText: String, lastMessagePathToFile: String
    ) = ChatUi.Base(id, title, companionId, lastMessageText, lastMessagePathToFile)
}