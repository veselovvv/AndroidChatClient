package com.veselovvv.androidchatclient.data.user

interface UserRepository {
    suspend fun createUser(
        name: String, email: String, password: String, roleId: String, photoPathToFile: String
    ): UsersData
    suspend fun fetchUser(userId: String): UsersData
    suspend fun fetchUserByEmail(email: String): UsersData
    suspend fun editUser(
        userId: String, name: String, email: String, password: String, photoPathToFile: String
    ): UsersData
    suspend fun banUser(userId: String, banned: Boolean): UsersData
    fun getUserId(): String //TODO dry here and in chat with messages
    fun getUserToken(): String //TODO dry here and in chat with messages
    fun cleanToken()

    class Base(
        private val cloudDataSource: UserCloudDataSource,
        private val userCloudMapper: UserCloudMapper,
        private val toUserDTOMapper: ToUserDTOMapper,
        private val toEditUserDTOMapper: ToEditUserDTOMapper,
        private val sessionManager: SessionManager
    ) : UserRepository {
        override suspend fun createUser(
            name: String, email: String, password: String, roleId: String, photoPathToFile: String
        ) = try {
            val userDTO = toUserDTOMapper.map(name, email, password, roleId, photoPathToFile)
            cloudDataSource.createUser(userDTO)
            UsersData.EmptySuccess()
        } catch (exception: Exception) {
            UsersData.Fail(exception)
        }

        override suspend fun fetchUser(userId: String) = try {
            val token = sessionManager.read().first
            val userCloud = cloudDataSource.fetchUser(token, userId)
            val user = userCloudMapper.map(userCloud)
            UsersData.Success(user)
        } catch (exception: Exception) {
            UsersData.Fail(exception)
        }

        override suspend fun fetchUserByEmail(email: String) = try {
            val token = sessionManager.read().first
            val userCloud = cloudDataSource.fetchUserByEmail(token, email)
            val user = userCloudMapper.map(userCloud)
            UsersData.Success(user)
        } catch (exception: Exception) {
            UsersData.Fail(exception)
        }

        override suspend fun editUser(
            userId: String, name: String, email: String, password: String, photoPathToFile: String
        ) = try {
            val token = sessionManager.read().first
            val editUserDTO = toEditUserDTOMapper.map(name, email, password, photoPathToFile)
            val userCloud = cloudDataSource.editUser(token, userId, editUserDTO)
            val user = userCloudMapper.map(userCloud)
            UsersData.Success(user)
        } catch (exception: Exception) {
            UsersData.Fail(exception)
        }

        override suspend fun banUser(userId: String, banned: Boolean) = try {
            val token = sessionManager.read().first
            cloudDataSource.banUser(token, userId, banned)
            UsersData.EmptySuccess()
        } catch (exception: Exception) {
            UsersData.Fail(exception)
        }

        override fun getUserId() = sessionManager.read().second
        override fun getUserToken() = sessionManager.read().first
        override fun cleanToken() = sessionManager.save(Pair(getUserId(), ""))
    }
}