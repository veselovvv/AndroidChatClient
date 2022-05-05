package com.veselovvv.androidchatclient.ui.chatwithmessages

import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatWithMessagesDomain
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatWithMessagesDomainToUiMapper

sealed class ChatsWithMessagesUi : Abstract.Object<Unit, ChatsWithMessagesCommunication> {
    class Success(
        private val chatWithMessages: ChatWithMessagesDomain,
        private val chatWithMessagesMapper: ChatWithMessagesDomainToUiMapper
    ) : ChatsWithMessagesUi() {
        override fun map(mapper: ChatsWithMessagesCommunication) {
            val chatWithMessagesUi = chatWithMessages.map(chatWithMessagesMapper)
            mapper.map(chatWithMessagesUi)
        }
    }

    class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : ChatsWithMessagesUi() {
        override fun map(mapper: ChatsWithMessagesCommunication) {
            val messageId = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_ERROR -> R.string.service_unavailable_message
                else -> R.string.something_went_wrong
            }

            val message = resourceProvider.getString(messageId)
            mapper.map(ChatWithMessagesUi.Fail(message))
        }
    }
}