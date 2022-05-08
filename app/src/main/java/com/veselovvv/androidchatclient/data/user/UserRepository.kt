package com.veselovvv.androidchatclient.data.user

import java.lang.Exception

interface UserRepository {
    suspend fun createUser(
        name: String, email: String, password: String, roleId: String, photoPathToFile: String
    ): UserData

    class Base(
        private val cloudDataSource: UserCloudDataSource,
        private val mapper: ToUserMapper
    ) : UserRepository {
        override suspend fun createUser(
            name: String, email: String, password: String, roleId: String, photoPathToFile: String
        ) = try {
            val userDTO = mapper.map(name, email, password, roleId, photoPathToFile)
            cloudDataSource.createUser(userDTO) //TODO need save result?
            UserData.RegisterSuccess()
        } catch (exception: Exception) {
            UserData.Fail(exception)
        }
    }
}