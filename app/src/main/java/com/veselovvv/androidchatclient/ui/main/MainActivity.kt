package com.veselovvv.androidchatclient.ui.main

import android.os.Bundle
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.ui.chats.ChatsFragment
import com.veselovvv.androidchatclient.ui.chats.NewChatFragment
import com.veselovvv.androidchatclient.ui.chatwithmessages.AddMemberFragment
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatWithMessagesFragment
import com.veselovvv.androidchatclient.ui.core.BaseActivity
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.ADD_MEMBER_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.BAN_USER_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHATS_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHAT_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.NEW_CHAT_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.SETTINGS_SCREEN
import com.veselovvv.androidchatclient.ui.user.BanUserFragment
import com.veselovvv.androidchatclient.ui.user.SettingsFragment

class MainActivity : BaseActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = (application as ChatApp).getMainViewModel()
        viewModel.observe(this, {
            when (it) {
                CHATS_SCREEN -> replaceFragment(R.id.container_main, ChatsFragment(), false)
                CHAT_SCREEN -> replaceFragment(R.id.container_main, ChatWithMessagesFragment())
                SETTINGS_SCREEN -> replaceFragment(R.id.container_main, SettingsFragment())
                NEW_CHAT_SCREEN -> replaceFragment(R.id.container_main, NewChatFragment())
                ADD_MEMBER_SCREEN -> replaceFragment(R.id.container_main, AddMemberFragment())
                BAN_USER_SCREEN -> replaceFragment(R.id.container_main, BanUserFragment())
                else -> throw IllegalStateException("Screen id is undefined: $it")
            }
        })
        viewModel.init()
    }

    override fun onBackPressed() {
        if (viewModel.navigateBack()) super.onBackPressed()
    }
}