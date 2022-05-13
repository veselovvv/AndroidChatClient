package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract

interface UserCloudMapper : Abstract.Mapper {
    fun map(userCloud: User): UserData
    
    class Base(private val userMapper: ToUserMapper) : UserCloudMapper {
        override fun map(userCloud: User) = userCloud.map(userMapper)
    }
}