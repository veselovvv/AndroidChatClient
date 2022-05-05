package com.veselovvv.androidchatclient.ui.chatwithmessages

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.veselovvv.androidchatclient.core.Abstract

interface ChatsWithMessagesCommunication : Abstract.Mapper {
    fun map(chatWithMessages: ChatWithMessagesUi)
    fun observe(owner: LifecycleOwner, observer: Observer<ChatWithMessagesUi>)

    class Base : ChatsWithMessagesCommunication {
        private val liveData = MutableLiveData<ChatWithMessagesUi>()

        override fun map(chatWithMessages: ChatWithMessagesUi) {
            liveData.value = chatWithMessages
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<ChatWithMessagesUi>) =
            liveData.observe(owner, observer)
    }
}