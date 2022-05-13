package com.veselovvv.androidchatclient.ui.user

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileInteractor
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.core.BaseFileUploadViewModel
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileCommunication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val userInteractor: UserInteractor,
    private val uploadFileInteractor: UploadFileInteractor,
    private val userMapper: UsersDomainToUiMapper,
    private val uploadFileMapper: UploadFileDomainToUiMapper,
    private val userCommunication: UserCommunication,
    private val uploadFileCommunication: UploadFileCommunication
) : BaseFileUploadViewModel(uploadFileInteractor, uploadFileMapper, uploadFileCommunication) {
    fun fetchUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.fetchUser(userId)
            val resultUi = resultDomain.map(userMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(userCommunication)
            }
        }
    }

    fun editUser(userId: String, name: String, email: String, password: String, photoPathToFile: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.editUser(userId, name, email, password, photoPathToFile)
            val resultUi = resultDomain.map(userMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(userCommunication)
            }
        }
    }

    fun observeUser(owner: LifecycleOwner, observer: Observer<UserUi>) =
        userCommunication.observe(owner, observer)

    fun getUserId() = userInteractor.getUserId()
    fun getUserToken() = userInteractor.getUserToken()
    fun cleanToken() = userInteractor.cleanToken()
}