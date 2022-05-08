package com.veselovvv.androidchatclient.domain.chatwithmessages

import com.veselovvv.androidchatclient.data.chatwithmessages.ChatWithMessagesData
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatsWithMessagesDataToDomainMapper

class BaseChatsWithMessagesDataToDomainMapper(
    private val chatWithMessagesMapper: ChatWithMessagesDataToDomainMapper
) : ChatsWithMessagesDataToDomainMapper {
    override fun map() = ChatsWithMessagesDomain.Empty()
    override fun map(chatWithMessages: ChatWithMessagesData) =
        ChatsWithMessagesDomain.Success(chatWithMessages, chatWithMessagesMapper)
    override fun map(exception: Exception) = ChatsWithMessagesDomain.Fail(exception)
}