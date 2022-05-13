package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.chats.ChatDomain

interface ChatDataToDomainMapper : Abstract.Mapper {
    fun map(
        id: String,
        title: String,
        companionId: String,
        lastMessageText: String,
        lastMessagePathToFile: String,
        photoPath: String
    ): ChatDomain
}
