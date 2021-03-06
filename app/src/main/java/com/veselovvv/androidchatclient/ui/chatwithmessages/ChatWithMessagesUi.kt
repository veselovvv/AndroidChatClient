package com.veselovvv.androidchatclient.ui.chatwithmessages

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.core.Retry
import com.veselovvv.androidchatclient.data.chatwithmessages.ChatDetails
import com.veselovvv.androidchatclient.data.message.Message

sealed class ChatWithMessagesUi {
    open fun map(progressLayout: FrameLayout) = Unit
    open fun map(hideMenuItems: HideMenuItems) = Unit
    open fun map(handleMessages: HandleMessages) = Unit

    open fun map(
        failLayout: LinearLayout,
        messageTextView: MaterialTextView,
        tryAgainButton: MaterialButton,
        retry: Retry
    ) = Unit

    object Progress : ChatWithMessagesUi() {
        override fun map(progressLayout: FrameLayout) {
            progressLayout.visibility = View.VISIBLE
        }
    }

    class Empty : ChatWithMessagesUi()

    class Base(
        private val chat: ChatDetails,
        private val messages: List<Message>
    ) : ChatWithMessagesUi() {
        override fun map(progressLayout: FrameLayout) {
            progressLayout.visibility = View.GONE
        }
        override fun map(hideMenuItems: HideMenuItems) = hideMenuItems.hide(chat)
        override fun map(handleMessages: HandleMessages) = handleMessages.fetchMessages(messages)
    }

    class Fail(private val message: String) : ChatWithMessagesUi() {
        override fun map(progressLayout: FrameLayout) {
            progressLayout.visibility = View.GONE
        }

        override fun map(
            failLayout: LinearLayout,
            messageTextView: MaterialTextView,
            tryAgainButton: MaterialButton,
            retry: Retry
        ) {
            failLayout.visibility = View.VISIBLE
            messageTextView.text = message
            tryAgainButton.setOnClickListener {
                retry.tryAgain()
                failLayout.visibility = View.GONE
            }
        }
    }
}