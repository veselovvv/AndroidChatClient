package com.veselovvv.androidchatclient.data.chats

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.message.Message

data class Chat(
    @SerializedName("id")
    private val id: String,
    @SerializedName("title")
    private var title: String?,
    private var companionId: String?,
    private var lastMessage: Message?,
    private var photoPathToFile: String?
) : Abstract.Object<ChatData, ToChatMapper> {
    val chatId get() = this.id
    var chatTitle
        get() = this.title ?: ""
        set(value) { this.title = value }
    var userCompanionId
        get() = this.companionId ?: ""
        set(value) { this.companionId = value }
    var lastChatMessage
        get() = this.lastMessage
        set(value) { this.lastMessage = value }
    var photoPath
        get() = this.photoPathToFile ?: ""
        set(value) { this.photoPathToFile = value }

    override fun map(mapper: ToChatMapper) =
        mapper.map(
            id,
            title ?: "",
            companionId ?: "",
            lastMessage?.messageText ?: "",
            lastMessage?.messagePathToFile ?: "",
            photoPath ?: ""
        )
}
