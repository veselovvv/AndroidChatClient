package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.data.login.LoginCloudDataSource
import com.veselovvv.androidchatclient.data.login.LoginRepository
import com.veselovvv.androidchatclient.data.login.ToLoginMapper
import com.veselovvv.androidchatclient.domain.login.BaseLoginDataToDomainMapper
import com.veselovvv.androidchatclient.domain.login.LoginInteractor
import com.veselovvv.androidchatclient.ui.login.BaseLoginDomainToUiMapper
import com.veselovvv.androidchatclient.ui.login.LoginCommunication
import com.veselovvv.androidchatclient.ui.login.LoginViewModel

class LoginModule(
    private val coreModule: CoreModule,
    private val userModule: UserModule
) : BaseModule<LoginViewModel> {
    override fun getViewModel() = LoginViewModel(
        getLoginInteractor(),
        BaseLoginDomainToUiMapper(coreModule.resourceProvider),
        LoginCommunication.Base()
    )

    private fun getLoginInteractor() = LoginInteractor.Base(
        LoginRepository.Base(
            LoginCloudDataSource.Base(userModule.getUserService(), coreModule.gson),
            ToLoginMapper.Base(),
            coreModule.sessionManager
        ), BaseLoginDataToDomainMapper()
    )
}