package com.veselovvv.androidchatclient.ui.chatwithmessages

import com.veselovvv.androidchatclient.data.message.Message

interface HandleMessages {
    fun fetchMessages(messages: List<Message>)
}