package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract

interface ToUserDTOMapper : Abstract.Mapper {
    fun map(
        name: String, email: String, password: String, roleId: String, photoPathToFile: String
    ): UserDTO

    class Base : ToUserDTOMapper {
        override fun map(
            name: String, email: String, password: String, roleId: String, photoPathToFile: String
        ) = UserDTO(name, email, password, roleId, photoPathToFile)
    }
}
