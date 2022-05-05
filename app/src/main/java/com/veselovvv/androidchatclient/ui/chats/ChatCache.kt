package com.veselovvv.androidchatclient.ui.chats

import android.content.Context
import com.veselovvv.androidchatclient.core.Read
import com.veselovvv.androidchatclient.core.Save

interface ChatCache : Save<Triple<String, String, String>>, Read<Triple<String, String, String>> {
    class Base(context: Context) : ChatCache {
        private val sharedPreferences =
            context.getSharedPreferences(context.packageName + CHAT_ID_FILENAME, Context.MODE_PRIVATE)

        override fun read() = Triple(
            sharedPreferences.getString(CHAT_ID_KEY, "") ?: "",
            sharedPreferences.getString(CHAT_TITLE_KEY, "") ?: "",
            sharedPreferences.getString(COMPANION_ID_KEY, "") ?: ""
        )

        override fun save(data: Triple<String, String, String>) =
            sharedPreferences.edit()
                .putString(CHAT_ID_KEY, data.first)
                .putString(CHAT_TITLE_KEY, data.second)
                .putString(COMPANION_ID_KEY, data.third)
                .apply()

        private companion object {
            const val CHAT_ID_FILENAME = "chatId"
            const val CHAT_ID_KEY = "chatIdKey"
            const val CHAT_TITLE_KEY = "chatTitleKey"
            const val COMPANION_ID_KEY = "companionIdKey"
        }
    }
}