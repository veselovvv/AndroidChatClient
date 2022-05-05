package com.veselovvv.androidchatclient.ui.message

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.veselovvv.androidchatclient.core.Abstract

interface MessageCommunication : Abstract.Mapper {
    fun map(messageUi: MessageUi)
    fun observe(owner: LifecycleOwner, observer: Observer<MessageUi>)

    class Base : MessageCommunication {
        private val liveData = MutableLiveData<MessageUi>()

        override fun map(messageUi: MessageUi) {
            liveData.value = messageUi
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<MessageUi>) =
            liveData.observe(owner, observer)
    }
}