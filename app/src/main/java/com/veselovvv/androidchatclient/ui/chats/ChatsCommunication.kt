package com.veselovvv.androidchatclient.ui.chats

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.veselovvv.androidchatclient.core.Abstract

interface ChatsCommunication : Abstract.Mapper {
    fun map(chats: List<ChatUi>)
    fun observe(owner: LifecycleOwner, observer: Observer<List<ChatUi>>)

    class Base : ChatsCommunication {
        private val listLiveData = MutableLiveData<List<ChatUi>>()

        override fun map(chats: List<ChatUi>) {
            listLiveData.value = chats
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<ChatUi>>) =
            listLiveData.observe(owner, observer)
    }
}