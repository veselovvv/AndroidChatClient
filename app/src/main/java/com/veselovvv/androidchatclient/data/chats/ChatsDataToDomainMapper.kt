package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.chats.ChatsDomain
import java.lang.Exception

interface ChatsDataToDomainMapper : Abstract.Mapper {
    fun map(chats: List<ChatData>): ChatsDomain
    fun map(exception: Exception): ChatsDomain
}