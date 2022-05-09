package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.ui.user.UserUi

class UserDomain(
    private val id: String,
    private val name: String,
    private val email: String,
    private val password: String,
    private val photoPathToFile: String
) : Abstract.Object<UserUi, UserDomainToUiMapper> {
    override fun map(mapper: UserDomainToUiMapper) = mapper.map(id, name, email, password, photoPathToFile)
}