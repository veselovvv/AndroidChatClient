package com.veselovvv.androidchatclient.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.ui.chats.ChatsFragment
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatWithMessagesFragment
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHATS_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHAT_SCREEN

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
                CHAT_SCREEN -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_main, ChatWithMessagesFragment())
                        .addToBackStack(null)
                        .commit()
                }
                else -> throw IllegalStateException("Screen id is undefined: $it")
            }
        })
        viewModel.init()
    }

    override fun onBackPressed() {
        if (viewModel.navigateBack()) super.onBackPressed()
    }
}