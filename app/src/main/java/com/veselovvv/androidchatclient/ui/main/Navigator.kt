package com.veselovvv.androidchatclient.ui.main

import android.content.Context
import com.veselovvv.androidchatclient.core.Read
import com.veselovvv.androidchatclient.core.Save

interface Navigator : Save<Int>, Read<Int> {
    class Base(context: Context) : Navigator {
        private val sharedPreferences =
            context.getSharedPreferences(context.packageName + NAVIGATOR_FILE_NAME, Context.MODE_PRIVATE)

        override fun save(data: Int) = sharedPreferences.edit().putInt(CURRENT_SCREEN_KEY, data).apply()
        override fun read(): Int = sharedPreferences.getInt(CURRENT_SCREEN_KEY, 0)

        private companion object {
            const val NAVIGATOR_FILE_NAME = "navigation"
            const val CURRENT_SCREEN_KEY = "screenId"
        }
    }
}

class Screens {
    companion object {
        const val CHATS_SCREEN = 0
        const val CHAT_SCREEN = 1
    }
}