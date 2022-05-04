package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract
import java.util.*

interface ToChatMapper : Abstract.Mapper {
    fun map(id: UUID, title: String): ChatData

    class Base : ToChatMapper {
        override fun map(id: UUID, title: String) = ChatData(id, title)
    }
}
