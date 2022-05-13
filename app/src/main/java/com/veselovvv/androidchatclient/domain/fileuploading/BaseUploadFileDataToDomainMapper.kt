package com.veselovvv.androidchatclient.domain.fileuploading

import com.veselovvv.androidchatclient.data.fileuploading.UploadFileDataToDomainMapper

class BaseUploadFileDataToDomainMapper : UploadFileDataToDomainMapper {
    override fun map(filePath: String) = UploadFileDomain.Success(filePath)
    override fun map(exception: Exception) = UploadFileDomain.Fail(exception)
}