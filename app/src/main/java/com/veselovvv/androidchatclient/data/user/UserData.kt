package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.user.UserDomain

class UserData(
    private val id: String,
    private val name: String,
    private val email: String,
    private val password: String,
    private val photoPathToFile: String,
    private val role: String
) : Abstract.Object<UserDomain, UserDataToDomainMapper> {
    override fun map(mapper: UserDataToDomainMapper) =
        mapper.map(id, name, email, password, photoPathToFile, role)
}