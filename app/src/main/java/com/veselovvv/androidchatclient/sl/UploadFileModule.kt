package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.data.fileuploading.UploadFileCloudDataSource
import com.veselovvv.androidchatclient.data.fileuploading.UploadFileRepository
import com.veselovvv.androidchatclient.data.fileuploading.UploadFileService
import com.veselovvv.androidchatclient.domain.fileuploading.BaseUploadFileDataToDomainMapper
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileInteractor
import com.veselovvv.androidchatclient.ui.fileuploading.BaseUploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileCommunication

class UploadFileModule(private val coreModule: CoreModule) {
    fun getUploadFileInteractor() = UploadFileInteractor.Base(
        UploadFileRepository.Base(
            UploadFileCloudDataSource.Base(getUploadFileService()),
            coreModule.sessionManager
        ), BaseUploadFileDataToDomainMapper(),
        coreModule.fileProvider
    )

    fun getUploadFileCommunication() = UploadFileCommunication.Base()
    fun getUploadFileDomainToUiMapper() = BaseUploadFileDomainToUiMapper(coreModule.resourceProvider)
    private fun getUploadFileService() = coreModule.makeService(UploadFileService::class.java)
}