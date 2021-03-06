package com.veselovvv.androidchatclient.data.user

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface UserCloudDataSource {
    suspend fun createUser(user: UserDTO)
    suspend fun fetchUser(token: String, userId: String): User
    suspend fun fetchUserByEmail(token: String, email: String): User
    suspend fun editUser(token: String, userId: String, editUserDTO: EditUserDTO): User
    suspend fun banUser(token: String, userId: String, banned: Boolean)

    class Base(private val service: UserService, private val gson: Gson) : UserCloudDataSource {
        private val type = object : TypeToken<User>() {}.type

        override suspend fun createUser(user: UserDTO) = service.createUser(user)

        override suspend fun fetchUser(token: String, userId: String): User =
            gson.fromJson(service.getUser(token, userId).string(), type)

        override suspend fun fetchUserByEmail(token: String, email: String): User =
            gson.fromJson(service.getUserByEmail(token, email).string(), type)

        override suspend fun editUser(token: String, userId: String, editUserDTO: EditUserDTO): User =
            gson.fromJson(service.editUser(token, userId, editUserDTO).string(), type)

        override suspend fun banUser(token: String, userId: String, banned: Boolean) =
            service.banUser(token, userId, banned)
    }
}
