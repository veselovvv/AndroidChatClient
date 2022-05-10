package com.veselovvv.androidchatclient.ui.user

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileInteractor
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileCommunication
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val userInteractor: UserInteractor, //TODO dry here and further, all that is used in Chats View Model
    private val uploadFileInteractor: UploadFileInteractor,
    private val userMapper: UsersDomainToUiMapper,
    private val uploadFileMapper: UploadFileDomainToUiMapper,
    private val userCommunication: UserCommunication,
    private val uploadFileCommunication: UploadFileCommunication
) : ViewModel() {
    private var pathToFile = "" //TODO dry here and further
    fun getPathToFile() = pathToFile
    fun setPathToFile(path: String) { pathToFile = path }

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

    fun observeUser(owner: LifecycleOwner, observer: Observer<UserUi>) = //TODO + rename to observe?
        userCommunication.observe(owner, observer)

    fun uploadFile(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = uploadFileInteractor.uploadFile(uri)
            val resultUi = resultDomain.map(uploadFileMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(uploadFileCommunication)
            }
        }
    }

    fun observeFileUploading(owner: LifecycleOwner, observer: Observer<UploadFileUi>) =
        uploadFileCommunication.observe(owner, observer)

    fun getUserId() = userInteractor.getUserId()
    fun getUserToken() = userInteractor.getUserToken()
}