package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.chats.ChatDomain
import java.util.*

data class ChatData(
    private val id: UUID, private val title: String
) : Abstract.Object<ChatDomain, ChatDataToDomainMapper> {
    override fun map(mapper: ChatDataToDomainMapper) = mapper.map(id, title)
}
