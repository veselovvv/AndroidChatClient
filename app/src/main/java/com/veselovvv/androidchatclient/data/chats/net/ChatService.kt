package com.veselovvv.androidchatclient.data.chats.net

import com.veselovvv.androidchatclient.data.message.MessageDTO
import okhttp3.ResponseBody
import retrofit2.http.*

interface ChatService {
    @GET("chats/{chatId}")
    suspend fun getChat(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String
    ): ResponseBody

    @POST("chats/direct/{userId}/send")
    suspend fun sendDirectMessage(
        @Header("Authorization") token: String,
        @Body message: MessageDTO,
        @Path("userId") userId: String
    )

    @POST("chats/group/{groupId}/send")
    suspend fun sendGroupMessage(
        @Header("Authorization") token: String,
        @Body message: MessageDTO,
        @Path("groupId") groupId: String
    )
}