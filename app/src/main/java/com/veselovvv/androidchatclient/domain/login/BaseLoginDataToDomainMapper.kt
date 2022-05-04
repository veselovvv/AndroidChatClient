package com.veselovvv.androidchatclient.domain.login

import com.veselovvv.androidchatclient.data.login.LoginDataToDomainMapper

class BaseLoginDataToDomainMapper : LoginDataToDomainMapper {
    override fun map() = LoginDomain.Success()
    override fun map(exception: Exception) = LoginDomain.Fail(exception)
}