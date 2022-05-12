package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.user.UserDomain

interface UserDataToDomainMapper : Abstract.Mapper {
    fun map(
        id: String,
        name: String,
        email: String,
        password: String,
        photoPathToFile: String,
        role: String
    ): UserDomain
}