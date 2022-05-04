package com.veselovvv.androidchatclient.ui.chats

import com.veselovvv.androidchatclient.core.Abstract
import java.util.*

sealed class ChatUi : Abstract.Object<Unit, ChatUi.BaseMapper> {
    override fun map(mapper: BaseMapper) = Unit
    open fun open(chatListener: ChatsAdapter.ChatListener) = Unit

    object Progress : ChatUi()

    class Base(private val id: UUID, private val title: String) : ChatUi() {
        override fun map(mapper: BaseMapper) = mapper.map(id, title)
        override fun open(chatListener: ChatsAdapter.ChatListener) =
            chatListener.showChat(id, title)
    }

    class Fail(private val message: String) : ChatUi() {
        override fun map(mapper: BaseMapper) = mapper.map(message)
    }

    interface BaseMapper : Abstract.Mapper {
        fun map(id: UUID, title: String)
        fun map(text: String)
    }
}
