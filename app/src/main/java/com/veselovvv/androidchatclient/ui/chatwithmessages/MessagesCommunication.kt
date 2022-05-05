package com.veselovvv.androidchatclient.ui.chatwithmessages

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.data.messages.Message

interface MessagesCommunication : Abstract.Mapper {
    fun map(messages: List<Message>) //TODO MessageUI
    fun observe(owner: LifecycleOwner, observer: Observer<List<Message>>)

    class Base : MessagesCommunication {
        private val listLiveData = MutableLiveData<List<Message>>()

        override fun map(messages: List<Message>) {
            listLiveData.value = messages
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<Message>>) =
            listLiveData.observe(owner, observer)
    }
}