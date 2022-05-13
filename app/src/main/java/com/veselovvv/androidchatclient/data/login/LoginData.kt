package com.veselovvv.androidchatclient.data.login

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.login.LoginDomain

sealed class LoginData : Abstract.Object<LoginDomain, LoginDataToDomainMapper> {
    class Success : LoginData() {
        override fun map(mapper: LoginDataToDomainMapper) = mapper.map()
    }

    data class Fail(private val exception: Exception) : LoginData() {
        override fun map(mapper: LoginDataToDomainMapper) = mapper.map(exception)
    }
}