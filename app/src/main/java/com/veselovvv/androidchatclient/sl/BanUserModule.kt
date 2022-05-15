package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.ui.user.BanUserViewModel

class BanUserModule(private val userModule: UserModule) : BaseModule<BanUserViewModel> {
    override fun getViewModel() = BanUserViewModel(
        userModule.getUserInteractor(),
        userModule.getUsersDomainToUiMapper(),
        userModule.getUserCommunication()
    )
}