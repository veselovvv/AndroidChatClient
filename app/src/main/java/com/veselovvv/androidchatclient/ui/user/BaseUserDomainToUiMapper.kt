package com.veselovvv.androidchatclient.ui.user

import com.veselovvv.androidchatclient.domain.user.UserDomainToUiMapper

class BaseUserDomainToUiMapper : UserDomainToUiMapper {
    override fun map(
        id: String, name: String, email: String, password: String, photoPathToFile: String
    ) = UserUi.Base(id, name, email, password, photoPathToFile)
}