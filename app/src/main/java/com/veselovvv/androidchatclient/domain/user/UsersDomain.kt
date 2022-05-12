package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.data.user.UserData
import com.veselovvv.androidchatclient.data.user.UserDataToDomainMapper
import com.veselovvv.androidchatclient.ui.user.UsersUi
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class UsersDomain : Abstract.Object<UsersUi, UsersDomainToUiMapper> {
    class EmptySuccess : UsersDomain() {
        override fun map(mapper: UsersDomainToUiMapper) = mapper.map()
    }

    class Success(
        private val user: UserData,
        private val userMapper: UserDataToDomainMapper
    ) : UsersDomain() {
        override fun map(mapper: UsersDomainToUiMapper) = mapper.map(user.map(userMapper))
    }

    class Fail(private val exception: Exception) : UsersDomain() {
        override fun map(mapper: UsersDomainToUiMapper) = mapper.map(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
