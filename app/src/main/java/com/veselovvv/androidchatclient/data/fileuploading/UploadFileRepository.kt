package com.veselovvv.androidchatclient.data.fileuploading

import com.veselovvv.androidchatclient.data.user.SessionManager
import okhttp3.MultipartBody

interface UploadFileRepository {
    suspend fun uploadFile(file: MultipartBody.Part): UploadFileData

    class Base(
        private val cloudDataSource: UploadFileCloudDataSource,
        private val sessionManager: SessionManager
    ) : UploadFileRepository {
        override suspend fun uploadFile(file: MultipartBody.Part) = try {
            val token = sessionManager.read().first
            val filePath = cloudDataSource.uploadFile(token, file)
            UploadFileData.Success(filePath)
        } catch (exception: Exception) {
            UploadFileData.Fail(exception)
        }
    }
}