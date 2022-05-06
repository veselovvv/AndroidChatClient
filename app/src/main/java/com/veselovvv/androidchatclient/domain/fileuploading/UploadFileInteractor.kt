package com.veselovvv.androidchatclient.domain.fileuploading

import android.net.Uri
import com.veselovvv.androidchatclient.data.fileuploading.UploadFileDataToDomainMapper
import com.veselovvv.androidchatclient.data.fileuploading.UploadFileRepository

interface UploadFileInteractor {
    suspend fun uploadFile(fileUri: Uri): UploadFileDomain

    class Base(
        private val uploadFileRepository: UploadFileRepository,
        private val mapper: UploadFileDataToDomainMapper,
        private val fileProvider: FileProvider,
    ) : UploadFileInteractor {
        override suspend fun uploadFile(fileUri: Uri): UploadFileDomain {
            val file = fileProvider.getFile(fileUri)
            return uploadFileRepository.uploadFile(file).map(mapper)
        }
    }
}