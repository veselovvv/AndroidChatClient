package com.veselovvv.androidchatclient.domain.login

import com.veselovvv.androidchatclient.data.login.LoginDataToDomainMapper
import com.veselovvv.androidchatclient.data.login.LoginRepository

interface LoginInteractor {
    suspend fun login(email: String, password: String): LoginDomain

    class Base(
        private val loginRepository: LoginRepository,
        private val mapper: LoginDataToDomainMapper
    ) : LoginInteractor {
        override suspend fun login(email: String, password: String) =
            loginRepository.login(email, password).map(mapper)
    }
}