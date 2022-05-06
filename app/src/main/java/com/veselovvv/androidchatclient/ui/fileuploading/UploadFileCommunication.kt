package com.veselovvv.androidchatclient.ui.fileuploading

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.veselovvv.androidchatclient.core.Abstract

interface UploadFileCommunication : Abstract.Mapper {
    fun map(uploadFileUi: UploadFileUi)
    fun observe(owner: LifecycleOwner, observer: Observer<UploadFileUi>)

    class Base : UploadFileCommunication {
        private val liveData = MutableLiveData<UploadFileUi>()

        override fun map(uploadFileUi: UploadFileUi) {
            liveData.value = uploadFileUi
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UploadFileUi>) =
            liveData.observe(owner, observer)
    }
}