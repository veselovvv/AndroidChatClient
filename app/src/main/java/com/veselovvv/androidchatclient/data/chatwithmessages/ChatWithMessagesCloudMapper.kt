package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract

interface ChatWithMessagesCloudMapper : Abstract.Mapper {
    fun map(cloud: Abstract.Object<ChatWithMessagesData, ToChatWithMessagesMapper>): ChatWithMessagesData

    class Base(private val chatWithMessagesMapper: ToChatWithMessagesMapper) : ChatWithMessagesCloudMapper {
        override fun map(cloud: Abstract.Object<ChatWithMessagesData, ToChatWithMessagesMapper>) =
            cloud.map(chatWithMessagesMapper)
    }
}