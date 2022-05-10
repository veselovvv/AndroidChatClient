package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.core.Abstract

interface ToEditUserDTOMapper : Abstract.Mapper {
    fun map(name: String, email: String, password: String, photoPathToFile: String): EditUserDTO

    class Base : ToEditUserDTOMapper {
        override fun map(name: String, email: String, password: String, photoPathToFile: String) =
            EditUserDTO(name, email, password, photoPathToFile)
    }
}