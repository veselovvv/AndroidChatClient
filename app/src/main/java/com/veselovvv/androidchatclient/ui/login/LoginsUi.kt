package com.veselovvv.androidchatclient.ui.login

import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider

sealed class LoginsUi : Abstract.Object<Unit, LoginCommunication> {
    class Success : LoginsUi() {
        override fun map(mapper: LoginCommunication) = mapper.map(LoginUi.Success())
    }

    class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : LoginsUi() {
        override fun map(mapper: LoginCommunication) {
            val messageId = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_ERROR -> R.string.server_error_message
                else -> R.string.something_went_wrong
            }

            val message = resourceProvider.getString(messageId)
            mapper.map(LoginUi.Fail(message))
        }
    }
}