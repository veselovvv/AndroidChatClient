package com.veselovvv.androidchatclient.sl

import com.veselovvv.androidchatclient.data.chats.ChatService
import com.veselovvv.androidchatclient.data.chatwithmessages.*
import com.veselovvv.androidchatclient.data.message.MessageCloudDataSource
import com.veselovvv.androidchatclient.data.message.MessageRepository
import com.veselovvv.androidchatclient.data.message.ToMessageDTOMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.BaseChatWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.BaseChatsWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesInteractor
import com.veselovvv.androidchatclient.domain.message.BaseMessageDataToDomainMapper
import com.veselovvv.androidchatclient.domain.message.MessageInteractor
import com.veselovvv.androidchatclient.ui.chatwithmessages.*
import com.veselovvv.androidchatclient.ui.message.BaseMessageDomainToUiMapper
import com.veselovvv.androidchatclient.ui.message.MessageCommunication

class ChatWithMessagesModule(
    private val coreModule: CoreModule,
    private val uploadFileModule: UploadFileModule
) : BaseModule<ChatsWithMessagesViewModel> {
    override fun getViewModel() = ChatsWithMessagesViewModel(
        getChatsWithMessagesInteractor(),
        getMessageInteractor(),
        uploadFileModule.getUploadFileInteractor(),
        getChatsWithMessagesCommunication(),
        MessagesCommunication.Base(),
        MessageCommunication.Base(),
        uploadFileModule.getUploadFileCommunication(),
        getChatsWithMessagesDomainToUiMapper(),
        BaseMessageDomainToUiMapper(coreModule.resourceProvider),
        uploadFileModule.getUploadFileDomainToUiMapper(),
        coreModule.chatCache,
        coreModule.navigationCommunication
    )

    fun getChatsWithMessagesInteractor() = ChatsWithMessagesInteractor.Base(
        ChatsWithMessagesRepository.Base(
            getChatsWithMessagesCloudDataSource(),
            ChatWithMessagesCloudMapper.Base(ToChatWithMessagesMapper.Base()),
            ToEditChatSettingsDtoMapper.Base(),
            ToCreateChatDtoMapper.Base(),
            ToAddMemberDtoMapper.Base(),
            coreModule.sessionManager
        ), BaseChatsWithMessagesDataToDomainMapper(BaseChatWithMessagesDataToDomainMapper())
    )

    fun getChatsWithMessagesCloudDataSource() =
        ChatWithMessagesCloudDataSource.Base(getChatService(), coreModule.gson)

    fun getChatsWithMessagesDomainToUiMapper() = BaseChatsWithMessagesDomainToUiMapper(
        coreModule.resourceProvider, BaseChatWithMessagesDomainToUiMapper()
    )

    fun getChatsWithMessagesCommunication() = ChatsWithMessagesCommunication.Base()

    private fun getMessageInteractor() = MessageInteractor.Base(
        MessageRepository.Base(
            MessageCloudDataSource.Base(getChatService()),
            ToMessageDTOMapper.Base(),
            coreModule.sessionManager
        ), BaseMessageDataToDomainMapper()
    )

    private fun getChatService() = coreModule.makeService(ChatService::class.java)
}