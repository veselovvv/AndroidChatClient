package com.veselovvv.androidchatclient.ui.user

import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.user.UserDomain
import com.veselovvv.androidchatclient.domain.user.UserDomainToUiMapper

sealed class UsersUi : Abstract.Object<Unit, UserCommunication> {
    class EmptySuccess : UsersUi() {
        override fun map(mapper: UserCommunication) = mapper.map(UserUi.RegisterSuccess())
    }

    class Success(
        private val user: UserDomain,
        private val userMapper: UserDomainToUiMapper
    ) : UsersUi() {
        override fun map(mapper: UserCommunication) {
            val usersUi = user.map(userMapper)
            mapper.map(usersUi)
        }
    }

    class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : UsersUi() {
        override fun map(mapper: UserCommunication) {
            val messageId = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_ERROR -> R.string.server_error_message
                else -> R.string.something_went_wrong
            }

            val message = resourceProvider.getString(messageId)
            mapper.map(UserUi.Fail(message))
        }
    }
}
