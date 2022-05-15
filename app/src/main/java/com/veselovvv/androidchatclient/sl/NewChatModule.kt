package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.ui.chats.NewChatViewModel

class NewChatModule(
    private val userModule: UserModule,
    private val chatWithMessagesModule: ChatWithMessagesModule
) : BaseModule<NewChatViewModel> {
    override fun getViewModel() = NewChatViewModel(
        userModule.getUserInteractor(),
        userModule.getUsersDomainToUiMapper(),
        userModule.getUserCommunication(),
        chatWithMessagesModule.getChatsWithMessagesInteractor(),
        chatWithMessagesModule.getChatsWithMessagesCommunication(),
        chatWithMessagesModule.getChatsWithMessagesDomainToUiMapper()
    )
}