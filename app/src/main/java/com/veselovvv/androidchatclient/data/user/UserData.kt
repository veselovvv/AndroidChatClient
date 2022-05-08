package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.user.UserDomain

sealed class UserData : Abstract.Object<UserDomain, UserDataToDomainMapper> {
    class RegisterSuccess : UserData() {
        override fun map(mapper: UserDataToDomainMapper) = mapper.map()
    }

    data class Fail(private val exception: Exception) : UserData() {
        override fun map(mapper: UserDataToDomainMapper) = mapper.map(exception)
    }
}
