package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.user.UsersDomain

sealed class UsersData : Abstract.Object<UsersDomain, UsersDataToDomainMapper> {
    class EmptySuccess : UsersData() {
        override fun map(mapper: UsersDataToDomainMapper) = mapper.map()
    }

    data class Success(private val user: UserData) : UsersData() {
        override fun map(mapper: UsersDataToDomainMapper) = mapper.map(user)
    }

    data class Fail(private val exception: Exception) : UsersData() {
        override fun map(mapper: UsersDataToDomainMapper) = mapper.map(exception)
    }
}
