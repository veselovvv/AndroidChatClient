package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.ui.chatwithmessages.AddMemberViewModel

class AddMemberModule(
    private val coreModule: CoreModule,
    private val userModule: UserModule,
    private val chatWithMessagesModule: ChatWithMessagesModule
) : BaseModule<AddMemberViewModel> {
    override fun getViewModel() = AddMemberViewModel(
        userModule.getUserInteractor(),
        userModule.getUsersDomainToUiMapper(),
        userModule.getUserCommunication(),
        chatWithMessagesModule.getChatsWithMessagesInteractor(),
        chatWithMessagesModule.getChatsWithMessagesCommunication(),
        chatWithMessagesModule.getChatsWithMessagesDomainToUiMapper(),
        coreModule.chatCache
    )
}