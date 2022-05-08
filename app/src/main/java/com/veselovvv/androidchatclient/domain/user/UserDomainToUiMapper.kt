package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.data.user.net.User
import com.veselovvv.androidchatclient.ui.user.UsersUi

interface UserDomainToUiMapper : Abstract.Mapper {
    fun map(): UsersUi
    fun map(errorType: ErrorType): UsersUi
}