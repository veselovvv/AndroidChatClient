package com.veselovvv.androidchatclient.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.ui.addmember.AddMemberFragment
import com.veselovvv.androidchatclient.ui.banuser.BanUserFragment
import com.veselovvv.androidchatclient.ui.chats.ChatsFragment
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatWithMessagesFragment
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.ADD_MEMBER_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.BAN_USER_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHATS_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHAT_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.NEW_CHAT_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.SETTINGS_SCREEN
import com.veselovvv.androidchatclient.ui.newchat.NewChatFragment
import com.veselovvv.androidchatclient.ui.user.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = (application as ChatApp).mainViewModel
        viewModel.observe(this, {
            when (it) {
                CHATS_SCREEN -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_main, ChatsFragment())
                        .commit()
                }
                CHAT_SCREEN -> replaceFragment(ChatWithMessagesFragment())
                SETTINGS_SCREEN -> replaceFragment(SettingsFragment())
                NEW_CHAT_SCREEN -> replaceFragment(NewChatFragment())
                ADD_MEMBER_SCREEN -> replaceFragment(AddMemberFragment())
                BAN_USER_SCREEN -> replaceFragment(BanUserFragment())
                else -> throw IllegalStateException("Screen id is undefined: $it")
            }
        })
        viewModel.init()
    }

    override fun onBackPressed() {
        if (viewModel.navigateBack()) super.onBackPressed()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}