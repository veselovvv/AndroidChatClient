package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract

interface ToChatMapper : Abstract.Mapper {
    fun map(
        id: String,
        title: String,
        companionId: String,
        lastMessageText: String,
        lastMessagePathToFile: String,
        photoPath: String
    ): ChatData

    class Base : ToChatMapper {
        override fun map(
            id: String,
            title: String,
            companionId: String,
            lastMessageText: String,
            lastMessagePathToFile: String,
            photoPath: String
        ) = ChatData(id, title, companionId, lastMessageText, lastMessagePathToFile, photoPath)
    }
}
