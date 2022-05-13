package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.data.chats.ChatDataToDomainMapper

class BaseChatDataToDomainMapper : ChatDataToDomainMapper {
    override fun map(
        id: String,
        title: String,
        companionId: String,
        lastMessageText: String,
        lastMessagePathToFile: String,
        photoPath: String
    ) = ChatDomain(id, title, companionId, lastMessageText, lastMessagePathToFile, photoPath)
}