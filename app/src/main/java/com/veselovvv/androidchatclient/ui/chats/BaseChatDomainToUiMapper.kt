package com.veselovvv.androidchatclient.ui.chats

import com.veselovvv.androidchatclient.domain.chats.ChatDomainToUiMapper

class BaseChatDomainToUiMapper : ChatDomainToUiMapper {
    override fun map(
        id: String,
        title: String,
        companionId: String,
        lastMessageText: String,
        lastMessagePathToFile: String,
        photoPath: String
    ) = ChatUi.Base(id, title, companionId, lastMessageText, lastMessagePathToFile, photoPath)
}