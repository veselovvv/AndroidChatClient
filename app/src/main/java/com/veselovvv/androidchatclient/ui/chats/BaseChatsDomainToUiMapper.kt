package com.veselovvv.androidchatclient.ui.chats

import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.chats.ChatDomain
import com.veselovvv.androidchatclient.domain.chats.ChatDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chats.ChatsDomainToUiMapper

class BaseChatsDomainToUiMapper(
    private val resourceProvider: ResourceProvider,
    private val chatMapper: ChatDomainToUiMapper
) : ChatsDomainToUiMapper {
    override fun map(chats: List<ChatDomain>) = ChatsUi.Success(chats, chatMapper)
    override fun map(errorType: ErrorType) = ChatsUi.Fail(errorType, resourceProvider)
}