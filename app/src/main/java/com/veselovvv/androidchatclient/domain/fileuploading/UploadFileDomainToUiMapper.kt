package com.veselovvv.androidchatclient.domain.fileuploading

import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFilesUi

interface UploadFileDomainToUiMapper : Abstract.Mapper {
    fun map(filePath: String): UploadFilesUi
    fun map(errorType: ErrorType): UploadFilesUi
}