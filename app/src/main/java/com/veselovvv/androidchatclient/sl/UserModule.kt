package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.data.user.*
import com.veselovvv.androidchatclient.domain.user.BaseUserDataToDomainMapper
import com.veselovvv.androidchatclient.domain.user.BaseUsersDataToDomainMapper
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.ui.user.BaseUserDomainToUiMapper
import com.veselovvv.androidchatclient.ui.user.BaseUsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.user.UserCommunication

class UserModule(private val coreModule: CoreModule) {
    fun getUserInteractor() = UserInteractor.Base(
        UserRepository.Base(
            getUserCloudDataSource(),
            UserCloudMapper.Base(ToUserMapper.Base()), ToUserDTOMapper.Base(),
            ToEditUserDTOMapper.Base(),
            coreModule.sessionManager
        ), BaseUsersDataToDomainMapper(BaseUserDataToDomainMapper())
    )

    fun getUserCloudDataSource() = UserCloudDataSource.Base(getUserService(), coreModule.gson)
    fun getUserService() = coreModule.makeService(UserService::class.java)
    fun getUserCommunication() = UserCommunication.Base()
    fun getUsersDomainToUiMapper() = BaseUsersDomainToUiMapper(
        coreModule.resourceProvider, BaseUserDomainToUiMapper()
    )
}