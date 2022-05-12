package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract

interface ToAddMemberDtoMapper : Abstract.Mapper {
    fun map(userId: String, isChatAdmin: String): AddMemberDto

    class Base : ToAddMemberDtoMapper {
        override fun map(userId: String, isChatAdmin: String) = AddMemberDto(userId, isChatAdmin)
    }
}