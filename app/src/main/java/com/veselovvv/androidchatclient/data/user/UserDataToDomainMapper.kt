package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.user.UserDomain

interface UserDataToDomainMapper : Abstract.Mapper {
    fun map(): UserDomain
    fun map(exception: Exception): UserDomain
}