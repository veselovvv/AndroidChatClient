package com.veselovvv.androidchatclient.data.user

import com.google.gson.annotations.SerializedName
import com.veselovvv.androidchatclient.core.Abstract

data class User(
    @SerializedName("id")
    private val id: String,
    @SerializedName("name")
    private val name: String,
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String,
    @SerializedName("role")
    private val role: Role,
    @SerializedName("photoPathToFile")
    private val photoPathToFile: String?
) : Abstract.Object<UserData, ToUserMapper> {
    val userId get() = this.id
    val userName get() = this.name
    val photoPath get() = this.photoPathToFile ?: ""

    override fun map(mapper: ToUserMapper) =
        mapper.map(id, name, email, password, photoPathToFile ?: "", role.roleName)
}

data class Role(
    @SerializedName("name")
    private val name: String
) {
    val roleName get() = this.name
}