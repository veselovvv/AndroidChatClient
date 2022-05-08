package com.veselovvv.androidchatclient.data.chatwithmessages

import com.veselovvv.androidchatclient.core.Abstract

interface ToEditChatSettingsDtoMapper : Abstract.Mapper {
    fun map(banned: Boolean, sendNotifications: Boolean): EditChatSettingsDto

    class Base : ToEditChatSettingsDtoMapper {
        override fun map(banned: Boolean, sendNotifications: Boolean) =
            EditChatSettingsDto(banned, sendNotifications)
    }
}