package com.veselovvv.androidchatclient.domain.login

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.login.LoginsUi

interface LoginDomainToUiMapper : Abstract.Mapper {
    fun map(): LoginsUi
    fun map(errorType: ErrorType): LoginsUi
}