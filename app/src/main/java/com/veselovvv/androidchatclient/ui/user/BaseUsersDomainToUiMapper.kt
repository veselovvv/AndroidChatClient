package com.veselovvv.androidchatclient.ui.user

import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.user.UserDomain
import com.veselovvv.androidchatclient.domain.user.UserDomainToUiMapper
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper

class BaseUsersDomainToUiMapper(
    private val resourceProvider: ResourceProvider,
    private val userMapper: UserDomainToUiMapper
) : UsersDomainToUiMapper {
    override fun map() = UsersUi.EmptySuccess()
    override fun map(user: UserDomain) = UsersUi.Success(user, userMapper)
    override fun map(errorType: ErrorType) = UsersUi.Fail(errorType, resourceProvider)
}