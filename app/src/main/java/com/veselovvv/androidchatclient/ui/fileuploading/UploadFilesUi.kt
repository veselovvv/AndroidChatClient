package com.veselovvv.androidchatclient.ui.fileuploading

import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Abstract
import com.veselovvv.androidchatclient.core.ErrorType
import com.veselovvv.androidchatclient.core.ResourceProvider

sealed class UploadFilesUi : Abstract.Object<Unit, UploadFileCommunication> {
    class Success(private val filePath: String) : UploadFilesUi() {
        override fun map(mapper: UploadFileCommunication) = mapper.map(UploadFileUi.Success(filePath))
    }

    class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : UploadFilesUi() {
        override fun map(mapper: UploadFileCommunication) {
            val messageId = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_ERROR -> R.string.server_error_message
                else -> R.string.something_went_wrong
            }

            val message = resourceProvider.getString(messageId)
            mapper.map(UploadFileUi.Fail(message))
        }
    }
}
