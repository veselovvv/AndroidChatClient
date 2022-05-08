package com.veselovvv.androidchatclient.ui.user

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.veselovvv.androidchatclient.core.Abstract

interface UserCommunication : Abstract.Mapper {
    fun map(userUi: UserUi)
    fun observe(owner: LifecycleOwner, observer: Observer<UserUi>)

    class Base : UserCommunication {
        private val liveData = MutableLiveData<UserUi>()

        override fun map(userUi: UserUi) {
            liveData.value = userUi
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UserUi>) =
            liveData.observe(owner, observer)
    }
}