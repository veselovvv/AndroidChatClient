package com.veselovvv.androidchatclient.domain.login

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.login.LoginsUi
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class LoginDomain : Abstract.Object<LoginsUi, LoginDomainToUiMapper> {
    class Success : LoginDomain() {
        override fun map(mapper: LoginDomainToUiMapper) = mapper.map()
    }

    class Fail(private val exception: Exception): LoginDomain() {
        override fun map(mapper: LoginDomainToUiMapper) = mapper.map(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}