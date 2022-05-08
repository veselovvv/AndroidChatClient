package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.user.UsersUi
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class UserDomain : Abstract.Object<UsersUi, UserDomainToUiMapper> {
    class RegisterSuccess : UserDomain() {
        override fun map(mapper: UserDomainToUiMapper) = mapper.map()
    }

    class Fail(private val exception: Exception) : UserDomain() {
        override fun map(mapper: UserDomainToUiMapper) = mapper.map(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
