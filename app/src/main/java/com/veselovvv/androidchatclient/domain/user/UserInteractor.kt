package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.data.user.UserRepository
import com.veselovvv.androidchatclient.data.user.UsersDataToDomainMapper

interface UserInteractor {
    suspend fun createUser(
        name: String, email: String, password: String, roleId: String, photoPathToFile: String
    ): UsersDomain
    suspend fun fetchUser(userId: String): UsersDomain
    fun getUserId(): String

    class Base(
        private val userRepository: UserRepository,
        private val mapper: UsersDataToDomainMapper
    ) : UserInteractor {
        override suspend fun createUser(
            name: String, email: String, password: String, roleId: String, photoPathToFile: String
        ) = userRepository.createUser(name, email, password, roleId, photoPathToFile).map(mapper)

        override suspend fun fetchUser(userId: String) = userRepository.fetchUser(userId).map(mapper)
        override fun getUserId() = userRepository.getUserId()
    }
}