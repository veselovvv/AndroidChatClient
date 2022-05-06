package com.veselovvv.androidchatclient.data.fileuploading

import okhttp3.MultipartBody

interface UploadFileCloudDataSource {
    suspend fun uploadFile(token: String, file: MultipartBody.Part): String

    class Base(private val service: UploadFileService) : UploadFileCloudDataSource {
        override suspend fun uploadFile(token: String, file: MultipartBody.Part) =
            service.uploadFile(token, file)
    }
}