package com.veselovvv.androidchatclient.data.login

import com.veselovvv.androidchatclient.core.Abstract

interface ToLoginMapper : Abstract.Mapper {
    fun map(email: String, password: String): Login

    class Base : ToLoginMapper {
        override fun map(email: String, password: String) = Login(email, password)
    }
}
