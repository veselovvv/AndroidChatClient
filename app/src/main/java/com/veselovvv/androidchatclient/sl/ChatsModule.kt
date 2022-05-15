package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.data.chats.ChatsCloudDataSource
import com.veselovvv.androidchatclient.data.chats.ChatsCloudMapper
import com.veselovvv.androidchatclient.data.chats.ChatsRepository
import com.veselovvv.androidchatclient.data.chats.ToChatMapper
import com.veselovvv.androidchatclient.domain.chats.BaseChatDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chats.BaseChatsDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chats.ChatsInteractor
import com.veselovvv.androidchatclient.ui.chats.BaseChatDomainToUiMapper
import com.veselovvv.androidchatclient.ui.chats.BaseChatsDomainToUiMapper
import com.veselovvv.androidchatclient.ui.chats.ChatsCommunication
import com.veselovvv.androidchatclient.ui.chats.ChatsViewModel

class ChatsModule(
    private val coreModule: CoreModule,
    private val userModule: UserModule,
    private val chatWithMessagesModule: ChatWithMessagesModule
) : BaseModule<ChatsViewModel> {
    override fun getViewModel() = ChatsViewModel(
        getChatsInteractor(),
        userModule.getUserInteractor(),
        BaseChatsDomainToUiMapper(coreModule.resourceProvider, BaseChatDomainToUiMapper()),
        userModule.getUsersDomainToUiMapper(),
        ChatsCommunication.Base(),
        userModule.getUserCommunication(),
        coreModule.chatCache,
        coreModule.navigator,
        coreModule.navigationCommunication
    )

    private fun getChatsInteractor() = ChatsInteractor.Base(
        ChatsRepository.Base(
            ChatsCloudDataSource.Base(userModule.getUserService(), coreModule.gson),
            chatWithMessagesModule.getChatsWithMessagesCloudDataSource(),
            userModule.getUserCloudDataSource(),
            ChatsCloudMapper.Base(ToChatMapper.Base()),
            coreModule.sessionManager
        ), BaseChatsDataToDomainMapper(BaseChatDataToDomainMapper())
    )
}