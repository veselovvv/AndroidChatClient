package com.veselovvv.androidchatclient.data.login

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veselovvv.androidchatclient.data.users.net.UserService

interface LoginCloudDataSource {
    suspend fun login(login: Login): LoginResponse

    class Base(private val service: UserService, private val gson: Gson) : LoginCloudDataSource {
        private val type = object : TypeToken<LoginResponse>() {}.type

        override suspend fun login(login: Login): LoginResponse =
            gson.fromJson(service.login(login).string(), type)
    }
}