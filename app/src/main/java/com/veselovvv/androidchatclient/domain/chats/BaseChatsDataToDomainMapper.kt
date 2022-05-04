package com.veselovvv.androidchatclient.domain.chats

import com.veselovvv.androidchatclient.data.chats.ChatData
import com.veselovvv.androidchatclient.data.chats.ChatDataToDomainMapper
import com.veselovvv.androidchatclient.data.chats.ChatsDataToDomainMapper
import java.lang.Exception

class BaseChatsDataToDomainMapper(
    private val chatMapper: ChatDataToDomainMapper
) : ChatsDataToDomainMapper {
    override fun map(chats: List<ChatData>) = ChatsDomain.Success(chats, chatMapper)
    override fun map(exception: Exception) = ChatsDomain.Fail(exception)
}