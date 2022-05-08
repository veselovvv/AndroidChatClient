package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.data.user.UserDataToDomainMapper

class BaseUserDataToDomainMapper : UserDataToDomainMapper {
    override fun map() = UserDomain.RegisterSuccess()
    override fun map(exception: Exception) = UserDomain.Fail(exception)
}