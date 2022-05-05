package com.veselovvv.androidchatclient.data.chats.net

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.chats.ChatData
import com.veselovvv.androidchatclient.data.chats.ToChatMapper
import com.veselovvv.androidchatclient.data.messages.Message
import java.util.*

data class Chat(
    @SerializedName("id")
    private val id: UUID,
    @SerializedName("title")
    private var title: String?,
    private var companionId: String?,
    //TODO
    private var lastMessage: Message?
    /*@SerializedName("user")
    private val user: User?, //TODO need that?
    @SerializedName("userChatSettingsList")
    private val userChatSettingsList: List<UserChatSettings>*/
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

    override fun map(mapper: ToChatMapper) =
        mapper.map(id, title ?: "", companionId ?: "", lastMessage?.messageText ?: "")
}
