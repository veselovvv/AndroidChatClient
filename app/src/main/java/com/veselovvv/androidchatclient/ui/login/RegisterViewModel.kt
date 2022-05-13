package com.veselovvv.androidchatclient.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileInteractor
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.core.BaseFileUploadViewModel
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileCommunication
import com.veselovvv.androidchatclient.ui.user.UserCommunication
import com.veselovvv.androidchatclient.ui.user.UserUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val userInteractor: UserInteractor,
    private val uploadFileInteractor: UploadFileInteractor,
    private val mapper: UsersDomainToUiMapper,
    private val uploadFileMapper: UploadFileDomainToUiMapper,
    private val communication: UserCommunication,
    private val uploadFileCommunication: UploadFileCommunication
) : BaseFileUploadViewModel(uploadFileInteractor, uploadFileMapper, uploadFileCommunication) {
    fun register(
        name: String, email: String, password: String, roleId: String, photoPathToFile: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.createUser(name, email, password, roleId, photoPathToFile)
            val resultUi = resultDomain.map(mapper)
            withContext(Dispatchers.Main) {
                resultUi.map(communication)
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<UserUi>) =
        communication.observe(owner, observer)
}