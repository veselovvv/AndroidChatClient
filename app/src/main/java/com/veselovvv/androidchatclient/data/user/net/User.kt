package com.veselovvv.androidchatclient.data.user.net

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    @SerializedName("id")
    private val id: UUID,
    @SerializedName("name")
    private val name: String
    /*@SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String,
    @SerializedName("banned")
    private val banned: Boolean,
    @SerializedName("role")
    private val role: Role,
    @SerializedName("photoPathToFile")
    private val photoPathToFile: String?,
    @SerializedName("userChatSettings")
    private val userChatSettings: List<UserChatSettings>*/
) /*: Abstract.Object<UserData, ToUserMapper> */{
    val userId get() = this.id
    val userName get() = this.name

    /*override fun map(mapper: ToUserMapper) =
        mapper.map(id, name, email, password, banned, role, photoPathToFile, userChatSettings)*/
}
