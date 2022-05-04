package com.veselovvv.androidchatclient.ui.login

import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.login.LoginDomainToUiMapper

class BaseLoginDomainToUiMapper(private val resourceProvider: ResourceProvider) : LoginDomainToUiMapper {
    override fun map() = LoginsUi.Success()
    override fun map(errorType: ErrorType) = LoginsUi.Fail(errorType, resourceProvider)
}