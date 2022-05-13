package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.ui.chats.ChatUi

class ChatDomain(
    private val id: String,
    private val title: String,
    private val companionId: String,
    private val lastMessageText: String,
    private val lastMessagePathToFile: String,
    private val photoPath: String
) : Abstract.Object<ChatUi, ChatDomainToUiMapper> {
    override fun map(mapper: ChatDomainToUiMapper) =
        mapper.map(id, title, companionId, lastMessageText, lastMessagePathToFile, photoPath)
}