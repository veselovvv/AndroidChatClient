package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.data.user.UserDataToDomainMapper
import com.veselovvv.androidchatclient.data.user.UserRepository

interface UserInteractor {
    suspend fun createUser(
        name: String, email: String, password: String, roleId: String, photoPathToFile: String
    ): UserDomain

    class Base(
        private val userRepository: UserRepository,
        private val mapper: UserDataToDomainMapper
    ) : UserInteractor {
        override suspend fun createUser(
            name: String, email: String, password: String, roleId: String, photoPathToFile: String
        ) = userRepository.createUser(name, email, password, roleId, photoPathToFile).map(mapper)
    }
}