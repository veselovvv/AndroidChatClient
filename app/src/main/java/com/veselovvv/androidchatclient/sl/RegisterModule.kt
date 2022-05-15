package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.ui.login.RegisterViewModel

class RegisterModule(
    private val userModule: UserModule,
    private val uploadFileModule: UploadFileModule
) : BaseModule<RegisterViewModel> {
    override fun getViewModel() = RegisterViewModel(
        userModule.getUserInteractor(),
        uploadFileModule.getUploadFileInteractor(),
        userModule.getUsersDomainToUiMapper(),
        uploadFileModule.getUploadFileDomainToUiMapper(),
        userModule.getUserCommunication(),
        uploadFileModule.getUploadFileCommunication()
    )
}