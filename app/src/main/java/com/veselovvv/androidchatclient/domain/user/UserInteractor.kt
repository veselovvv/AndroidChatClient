package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.data.user.UserRepository
import com.veselovvv.androidchatclient.data.user.UsersDataToDomainMapper

interface UserInteractor {
    suspend fun createUser(
        name: String, email: String, password: String, roleId: String, photoPathToFile: String
    ): UsersDomain
    suspend fun fetchUser(userId: String): UsersDomain
    suspend fun fetchUserByEmail(email: String): UsersDomain
    suspend fun editUser(
        userId: String, name: String, email: String, password: String, photoPathToFile: String
    ): UsersDomain
    suspend fun banUser(userId: String, banned: Boolean): UsersDomain
    fun getUserId(): String
    fun getUserToken(): String
    fun cleanToken()

    class Base(
        private val userRepository: UserRepository,
        private val mapper: UsersDataToDomainMapper
    ) : UserInteractor {
        override suspend fun createUser(
            name: String, email: String, password: String, roleId: String, photoPathToFile: String
        ) = userRepository.createUser(name, email, password, roleId, photoPathToFile).map(mapper)

        override suspend fun fetchUser(userId: String) = userRepository.fetchUser(userId).map(mapper)
        override suspend fun fetchUserByEmail(email: String) = userRepository.fetchUserByEmail(email).map(mapper)

        override suspend fun editUser(
            userId: String, name: String, email: String, password: String, photoPathToFile: String
        ) = userRepository.editUser(userId, name, email, password, photoPathToFile).map(mapper)

        override suspend fun banUser(userId: String, banned: Boolean) =
            userRepository.banUser(userId, banned).map(mapper)

        override fun getUserId() = userRepository.getUserId()
        override fun getUserToken() = userRepository.getUserToken()
        override fun cleanToken() = userRepository.cleanToken()
    }
}