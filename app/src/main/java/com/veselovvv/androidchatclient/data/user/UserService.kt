package com.veselovvv.androidchatclient.data.user

import com.veselovvv.androidchatclient.data.login.Login
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserService {
    @POST("users/login")
    suspend fun login(@Body login: Login): ResponseBody

    @POST("users")
    suspend fun createUser(@Body user: UserDTO)

    @GET("users/{userId}/chats")
    suspend fun getChats(
        @Header("Authorization") token: String, @Path("userId") userId: String
    ): ResponseBody

    @GET("users/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String, @Path("userId") userId: String
    ): ResponseBody

    @GET("users/getByEmail/{email}")
    suspend fun getUserByEmail(
        @Header("Authorization") token: String, @Path("email") email: String
    ): ResponseBody

    @PUT("users/{userId}/edit")
    suspend fun editUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body editUserDTO: EditUserDTO
    ): ResponseBody

    @PUT("users/{userId}/ban")
    suspend fun banUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("banned") banned: Boolean
    )
}