package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.chats.ChatDomain
import java.util.*

interface ChatDataToDomainMapper : Abstract.Mapper {
    fun map(id: UUID, title: String, companionId: String, lastMessageText: String): ChatDomain
}
