package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.ui.user.UserUi

interface UserDomainToUiMapper : Abstract.Mapper {
    fun map(
        id: String,
        name: String,
        email: String,
        password: String,
        photoPathToFile: String,
        role: String
    ): UserUi
}