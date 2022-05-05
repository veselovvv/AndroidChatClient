package com.veselovvv.androidchatclient.data.user

import android.content.Context
import com.veselovvv.androidchatclient.core.Read
import com.veselovvv.androidchatclient.core.Save

interface SessionManager : Save<Pair<String, String>>, Read<Pair<String, String>> {
    class Base(context: Context) : SessionManager {
        private var sharedPreferences =
            context.getSharedPreferences(context.packageName + SESSION_TOKEN_FILENAME, Context.MODE_PRIVATE)

        override fun read() = Pair(
            sharedPreferences.getString(USER_TOKEN, null) ?: "",
            sharedPreferences.getString(USER_ID, null) ?: ""
        )

        override fun save(data: Pair<String, String>) =
            sharedPreferences.edit()
                .putString(USER_TOKEN, data.first)
                .putString(USER_ID, data.second)
                .apply()

        private companion object {
            const val SESSION_TOKEN_FILENAME = "sessionToken"
            const val USER_TOKEN = "userToken"
            const val USER_ID = "userId"
        }
    }
}