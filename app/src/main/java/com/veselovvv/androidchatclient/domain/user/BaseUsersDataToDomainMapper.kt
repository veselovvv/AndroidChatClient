package com.veselovvv.androidchatclient.domain.user

import com.veselovvv.androidchatclient.data.user.UserData
import com.veselovvv.androidchatclient.data.user.UserDataToDomainMapper
import com.veselovvv.androidchatclient.data.user.UsersDataToDomainMapper

class BaseUsersDataToDomainMapper(
    private val userMapper: UserDataToDomainMapper
) : UsersDataToDomainMapper {
    override fun map() = UsersDomain.RegisterSuccess()
    override fun map(user: UserData) = UsersDomain.Success(user, userMapper)
    override fun map(exception: Exception) = UsersDomain.Fail(exception)
}