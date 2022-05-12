package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract

interface ToUserMapper : Abstract.Mapper {
    fun map(
        id: String,
        name: String,
        email: String,
        password: String,
        photoPathToFile: String,
        role: String
    ): UserData

    class Base : ToUserMapper {
        override fun map(
            id: String,
            name: String,
            email: String,
            password: String,
            photoPathToFile: String,
            role: String
        ) = UserData(id, name, email, password, photoPathToFile, role)
    }
}
