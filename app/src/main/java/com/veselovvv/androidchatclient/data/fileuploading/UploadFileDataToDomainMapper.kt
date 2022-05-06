package com.veselovvv.androidchatclient.data.fileuploading

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomain

interface UploadFileDataToDomainMapper : Abstract.Mapper {
    fun map(filePath: String): UploadFileDomain
    fun map(exception: Exception): UploadFileDomain
}
