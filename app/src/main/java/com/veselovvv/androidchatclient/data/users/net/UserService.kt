package com.veselovvv.androidchatclient.data.users.net

import com.veselovvv.androidchatclient.data.login.Login
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserService {
    @POST("users/login")
    suspend fun login(@Body login: Login): ResponseBody

    @GET("users/{userId}/chats")
    suspend fun getChats(
        @Header("Authorization") token: String, @Path("userId") userId: String
    ): ResponseBody

    @GET("users/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String, @Path("userId") userId: String
    ): ResponseBody
}