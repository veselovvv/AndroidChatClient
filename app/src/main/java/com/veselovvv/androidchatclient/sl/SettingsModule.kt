package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.ui.user.SettingsViewModel

class SettingsModule(
    private val userModule: UserModule,
    private val uploadFileModule: UploadFileModule
) : BaseModule<SettingsViewModel> {
    override fun getViewModel() = SettingsViewModel(
        userModule.getUserInteractor(),
        uploadFileModule.getUploadFileInteractor(),
        userModule.getUsersDomainToUiMapper(),
        uploadFileModule.getUploadFileDomainToUiMapper(),
        userModule.getUserCommunication(),
        uploadFileModule.getUploadFileCommunication()
    )
}