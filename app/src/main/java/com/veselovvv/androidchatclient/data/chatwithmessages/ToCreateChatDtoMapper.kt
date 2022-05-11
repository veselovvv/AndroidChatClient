package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract

interface ToCreateChatDtoMapper : Abstract.Mapper {
    fun map(title: String, createdBy: String, userIds: List<String>): CreateChatDto

    class Base : ToCreateChatDtoMapper {
        override fun map(title: String, createdBy: String, userIds: List<String>) =
            CreateChatDto(title, createdBy, userIds)
    }
}