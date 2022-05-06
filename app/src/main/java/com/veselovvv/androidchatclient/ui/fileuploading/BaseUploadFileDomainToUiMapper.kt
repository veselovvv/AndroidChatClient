package com.veselovvv.androidchatclient.ui.fileuploading

import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomainToUiMapper

class BaseUploadFileDomainToUiMapper(
    private val resourceProvider: ResourceProvider
) : UploadFileDomainToUiMapper {
    override fun map(filePath: String) = UploadFilesUi.Success(filePath)
    override fun map(errorType: ErrorType) = UploadFilesUi.Fail(errorType, resourceProvider)
}