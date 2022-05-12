package com.veselovvv.androidchatclient.data.chats.net

import com.veselovvv.androidchatclient.data.chatwithmessages.AddMemberDto
import com.veselovvv.androidchatclient.data.chatwithmessages.CreateChatDto
import com.veselovvv.androidchatclient.data.chatwithmessages.EditChatSettingsDto
import com.veselovvv.androidchatclient.data.message.MessageDTO
import okhttp3.ResponseBody
import retrofit2.http.*

interface ChatService {
    @GET("chats/{chatId}")
    suspend fun getChat(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String
    ): ResponseBody

    @POST("chats/group")
    suspend fun createChat(
        @Header("Authorization") token: String,
        @Body createChatDto: CreateChatDto
    )

    @POST("chats/group/{groupId}/join")
    suspend fun addMember(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: String,
        @Body addMemberDto: AddMemberDto
    )

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

    @PUT("chats/{chatId}/{userId}/edit")
    suspend fun editChatSettings(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Path("userId") userId: String,
        @Body editChatSettings: EditChatSettingsDto
    ): ResponseBody

    @DELETE("chats/group/{groupId}/{userId}/leave")
    suspend fun leaveGroupChat(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: String,
        @Path("userId") userId: String
    )

    @DELETE("chats/{chatId}/delete")
    suspend fun deleteChat(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String
    )
}