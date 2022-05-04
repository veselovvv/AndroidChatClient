package com.veselovvv.androidchatclient.data.login

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.login.LoginDomain

interface LoginDataToDomainMapper : Abstract.Mapper {
    fun map(): LoginDomain
    fun map(exception: Exception): LoginDomain
}