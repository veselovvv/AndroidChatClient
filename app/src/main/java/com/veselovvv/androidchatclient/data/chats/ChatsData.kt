package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.chats.ChatsDomain
import java.lang.Exception

sealed class ChatsData : Abstract.Object<ChatsDomain, ChatsDataToDomainMapper> {
    data class Success(private val chats: List<ChatData>) : ChatsData() {
        override fun map(mapper: ChatsDataToDomainMapper) = mapper.map(chats)
    }

    data class Fail(private val exception: Exception) : ChatsData() {
        override fun map(mapper: ChatsDataToDomainMapper) = mapper.map(exception)
    }
}
