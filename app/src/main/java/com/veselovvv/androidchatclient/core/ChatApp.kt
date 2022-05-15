package com.veselovvv.androidchatclient.core

import android.app.Application
import com.veselovvv.androidchatclient.sl.*

class ChatApp : Application() {
    private val coreModule = CoreModule()
    private val userModule = UserModule(coreModule)
    private val uploadFileModule = UploadFileModule(coreModule)
    private val chatWithMessagesModule = ChatWithMessagesModule(coreModule, uploadFileModule)
    private val chatsModule = ChatsModule(coreModule, userModule, chatWithMessagesModule)
    private val addMemberModule = AddMemberModule(coreModule, userModule, chatWithMessagesModule)
    private val newChatModule = NewChatModule(userModule, chatWithMessagesModule)
    private val settingsModule = SettingsModule(userModule, uploadFileModule)
    private val banUserModule = BanUserModule(userModule)
    private val loginModule = LoginModule(coreModule, userModule)
    private val registerModule = RegisterModule(userModule, uploadFileModule)

    override fun onCreate() {
        super.onCreate()
        coreModule.init(this)
    }

    fun getMainViewModel() = coreModule.getViewModel()
    fun getChatWithMessagesViewModel() = chatWithMessagesModule.getViewModel()
    fun getChatsViewModel() = chatsModule.getViewModel()
    fun getAddMemberViewModel() = addMemberModule.getViewModel()
    fun getNewChatViewModel() = newChatModule.getViewModel()
    fun getSettingsViewModel() = settingsModule.getViewModel()
    fun getBanUserViewModel() = banUserModule.getViewModel()
    fun getLoginViewModel() = loginModule.getViewModel()
    fun getRegisterViewModel() = registerModule.getViewModel()
    fun getValidator() = coreModule.validator
    fun getImageLoader() = coreModule.imageLoader
}