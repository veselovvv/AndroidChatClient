package com.veselovvv.androidchatclient.domain.fileuploading

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFilesUi
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class UploadFileDomain : Abstract.Object<UploadFilesUi, UploadFileDomainToUiMapper> {
    class Success(private val filePath: String) : UploadFileDomain() {
        override fun map(mapper: UploadFileDomainToUiMapper) = mapper.map(filePath)
    }

    class Fail(private val exception: Exception) : UploadFileDomain() {
        override fun map(mapper: UploadFileDomainToUiMapper) = mapper.map(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
