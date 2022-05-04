package com.veselovvv.androidchatclient.data.chats

import com.veselovvv.androidchatclient.core.Abstract

interface ChatsCloudMapper : Abstract.Mapper {
    fun map(cloudList: List<Abstract.Object<ChatData, ToChatMapper>>): List<ChatData>

    class Base(private val chatMapper: ToChatMapper) : ChatsCloudMapper {
        override fun map(cloudList: List<Abstract.Object<ChatData, ToChatMapper>>) =
            cloudList.map { chatCloud -> chatCloud.map(chatMapper) }
    }
}