package com.veselovvv.androidchatclient.data.message

import com.veselovvv.androidchatclient.core.Abstract

interface ToMessageDTOMapper : Abstract.Mapper {
    fun map(text: String, pathToFile: String, chatId: String, userId: String): MessageDTO

    class Base : ToMessageDTOMapper {
        override fun map(text: String, pathToFile: String, chatId: String, userId: String) =
            MessageDTO(text, pathToFile, chatId, userId)
    }
}