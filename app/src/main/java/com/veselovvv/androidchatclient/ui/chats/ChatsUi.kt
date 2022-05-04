package com.veselovvv.androidchatclient.ui.chats

import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.chats.ChatDomain
import com.veselovvv.androidchatclient.domain.chats.ChatDomainToUiMapper

sealed class ChatsUi : Abstract.Object<Unit, ChatsCommunication> {
    class Success(
        private val chats: List<ChatDomain>,
        private val chatMapper: ChatDomainToUiMapper
    ) : ChatsUi() {
        override fun map(mapper: ChatsCommunication) {
            val chatsUi = chats.map { it.map(chatMapper) }
            mapper.map(chatsUi)
        }
    }

    class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : ChatsUi() {
        override fun map(mapper: ChatsCommunication) {
            val messageId = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_ERROR -> R.string.service_unavailable_message
                else -> R.string.something_went_wrong
            }

            val message = resourceProvider.getString(messageId)
            mapper.map(listOf(ChatUi.Fail(message)))
        }
    }
}
