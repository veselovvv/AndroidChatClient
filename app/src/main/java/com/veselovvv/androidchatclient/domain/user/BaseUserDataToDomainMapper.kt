package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.data.user.UserDataToDomainMapper

class BaseUserDataToDomainMapper : UserDataToDomainMapper {
    override fun map(
        id: String, name: String, email: String, password: String, photoPathToFile: String
    ) = UserDomain(id, name, email, password, photoPathToFile)
}