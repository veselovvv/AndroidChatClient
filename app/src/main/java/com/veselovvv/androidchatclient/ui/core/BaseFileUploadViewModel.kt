package com.veselovvv.androidchatclient.ui.core

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileInteractor
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileCommunication
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseFileUploadViewModel(
    private val uploadFileInteractor: UploadFileInteractor,
    private val uploadFileMapper: UploadFileDomainToUiMapper,
    private val uploadFileCommunication: UploadFileCommunication
) : ViewModel() {
    private var pathToFile = ""
    fun getPathToFile() = pathToFile
    fun setPathToFile(path: String) { pathToFile = path }

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
}