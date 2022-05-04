package com.veselovvv.androidchatclient.data.login

import com.veselovvv.androidchatclient.data.user.SessionManager

interface LoginRepository {
    suspend fun login(email: String, password: String): LoginData

    class Base(
        private val cloudDataSource: LoginCloudDataSource,
        private val mapper: ToLoginMapper,
        private val sessionManager: SessionManager
    ) : LoginRepository {
        override suspend fun login(email: String, password: String) = try {
            val login = mapper.map(email, password)
            val response = cloudDataSource.login(login)
            sessionManager.save(Pair(response.userToken, response.id))
            LoginData.Success()
        } catch (exception: Exception) {
            LoginData.Fail(exception)
        }
    }
}