package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.user.UsersDomain

interface UsersDataToDomainMapper : Abstract.Mapper {
    fun map(): UsersDomain
    fun map(user: UserData): UsersDomain
    fun map(exception: Exception): UsersDomain
}