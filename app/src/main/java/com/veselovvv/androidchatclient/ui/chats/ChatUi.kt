package com.veselovvv.androidchatclient.ui.chats

import com.veselovvv.androidchatclient.core.Abstract
import java.util.*

sealed class ChatUi : Abstract.Object<Unit, ChatUi.BaseMapper> {
    override fun map(mapper: BaseMapper) = Unit
    open fun open(chatListener: ChatsAdapter.ChatListener) = Unit

    object Progress : ChatUi()

    class Base(
        private val id: UUID,
        private val title: String,
        private val companionId: String,
        private val lastMessageText: String,
        private val lastMessagePathToFile: String,
        private val photoPath: String
    ) : ChatUi() {
        override fun map(mapper: BaseMapper) =
            mapper.map(id, title, lastMessageText, lastMessagePathToFile, photoPath)
        override fun open(chatListener: ChatsAdapter.ChatListener) =
            chatListener.showChat(id, title, companionId)
    }

    class Fail(private val message: String) : ChatUi() {
        override fun map(mapper: BaseMapper) = mapper.map(message)
    }

    class AuthFail(private val message: String) : ChatUi() {
        override fun map(mapper: BaseMapper) = mapper.map(message)
    }

    interface BaseMapper : Abstract.Mapper {
        fun map(
            id: UUID,
            title: String,
            lastMessageText: String,
            lastMessagePathToFile: String,
            photoPath: String
        )
        fun map(text: String)
    }
}
