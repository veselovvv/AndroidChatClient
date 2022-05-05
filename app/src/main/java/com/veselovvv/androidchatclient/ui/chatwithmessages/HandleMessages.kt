package com.veselovvv.androidchatclient.ui.chatwithmessages

import com.veselovvv.androidchatclient.data.messages.Message

interface HandleMessages {
    fun fetchMessages(messages: List<Message>)
}