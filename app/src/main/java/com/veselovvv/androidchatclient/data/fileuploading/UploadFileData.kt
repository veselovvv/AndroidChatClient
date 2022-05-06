package com.veselovvv.androidchatclient.data.fileuploading

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomain

sealed class UploadFileData : Abstract.Object<UploadFileDomain, UploadFileDataToDomainMapper> {
    data class Success(private val filePath: String) : UploadFileData() {
        override fun map(mapper: UploadFileDataToDomainMapper) = mapper.map(filePath)
    }

    data class Fail(private val exception: Exception) : UploadFileData() {
        override fun map(mapper: UploadFileDataToDomainMapper) = mapper.map(exception)
    }
}
