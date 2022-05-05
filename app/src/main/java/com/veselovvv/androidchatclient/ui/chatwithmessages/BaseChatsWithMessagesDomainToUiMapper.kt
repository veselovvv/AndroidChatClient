package com.veselovvv.androidchatclient.ui.chatwithmessages

import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatWithMessagesDomain
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatWithMessagesDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomainToUiMapper

class BaseChatsWithMessagesDomainToUiMapper(
    private val resourceProvider: ResourceProvider,
    private val chatWithMessagesMapper: ChatWithMessagesDomainToUiMapper
) : ChatsWithMessagesDomainToUiMapper {
    override fun map(chatWithMessages: ChatWithMessagesDomain) =
        ChatsWithMessagesUi.Success(chatWithMessages, chatWithMessagesMapper)
    override fun map(errorType: ErrorType) = ChatsWithMessagesUi.Fail(errorType, resourceProvider)
}