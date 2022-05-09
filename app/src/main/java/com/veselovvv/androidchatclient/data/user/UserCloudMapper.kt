package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.user.net.User

interface UserCloudMapper : Abstract.Mapper {
    fun map(userCloud: User): UserData
    
    class Base(private val userMapper: ToUserMapper) : UserCloudMapper {
        override fun map(userCloud: User) = userCloud.map(userMapper)
    }
}