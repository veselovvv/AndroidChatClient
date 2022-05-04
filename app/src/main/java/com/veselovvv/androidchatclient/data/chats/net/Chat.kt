package com.veselovvv.androidchatclient.data.chats.net

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.chats.ChatData
import com.veselovvv.androidchatclient.data.chats.ToChatMapper
import com.veselovvv.androidchatclient.data.user.net.UserChatSettings
import java.util.*

data class Chat(
    @SerializedName("id")
    private val id: UUID,
    @SerializedName("title")
    private var title: String?,
    /*@SerializedName("user")
    private val user: User?, //TODO need that?
    @SerializedName("userChatSettingsList")
    private val userChatSettingsList: List<UserChatSettings>*/
) : Abstract.Object<ChatData, ToChatMapper> {
    val chatId get() = this.id //TODO
    var chatTitle get() = this.title ?: "" //TODO
        set(value) { this.title = value }
    override fun map(mapper: ToChatMapper) = mapper.map(id, title ?: "") //TODO
}
