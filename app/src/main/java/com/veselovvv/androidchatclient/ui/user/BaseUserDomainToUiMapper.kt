package com.veselovvv.androidchatclient.ui.user

import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.user.UserDomainToUiMapper

class BaseUserDomainToUiMapper(private val resourceProvider: ResourceProvider) : UserDomainToUiMapper {
    override fun map() = UsersUi.RegisterSuccess()
    override fun map(errorType: ErrorType) = UsersUi.Fail(errorType, resourceProvider)
}