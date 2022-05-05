package com.veselovvv.androidchatclient.ui.message

import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider

sealed class MessagesUi : Abstract.Object<Unit, MessageCommunication> {
    class Success : MessagesUi() {
        override fun map(mapper: MessageCommunication) = mapper.map(MessageUi.Success())
    }

    class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : MessagesUi() {
        override fun map(mapper: MessageCommunication) {
            val messageId = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_ERROR -> R.string.server_error_message
                else -> R.string.something_went_wrong
            }

            val message = resourceProvider.getString(messageId)
            mapper.map(MessageUi.Fail(message))
        }
    }
}
