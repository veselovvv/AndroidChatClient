package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.data.chats.ChatDataToDomainMapper
import java.util.*

class BaseChatDataToDomainMapper : ChatDataToDomainMapper {
    override fun map(id: UUID, title: String) = ChatDomain(id, title)
}