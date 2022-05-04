package com.veselovvv.androidchatclient.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.veselovvv.androidchatclient.core.Abstract

interface LoginCommunication : Abstract.Mapper {
    fun map(loginUi: LoginUi)
    fun observe(owner: LifecycleOwner, observer: Observer<LoginUi>)

    class Base : LoginCommunication {
        private val liveData = MutableLiveData<LoginUi>()

        override fun map(loginUi: LoginUi) {
            liveData.value = loginUi
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<LoginUi>) =
            liveData.observe(owner, observer)
    }
}