package com.veselovvv.androidchatclient.ui.chatwithmessages

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.core.Read
import com.veselovvv.androidchatclient.data.message.Message
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesInteractor
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileInteractor
import com.veselovvv.androidchatclient.domain.message.MessageDomainToUiMapper
import com.veselovvv.androidchatclient.domain.message.MessageInteractor
import com.veselovvv.androidchatclient.ui.core.BaseFileUploadViewModel
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileCommunication
import com.veselovvv.androidchatclient.ui.main.NavigationCommunication
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.ADD_MEMBER_SCREEN
import com.veselovvv.androidchatclient.ui.message.MessageCommunication
import com.veselovvv.androidchatclient.ui.message.MessageUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatsWithMessagesViewModel(
    private val chatsWithMessagesInteractor: ChatsWithMessagesInteractor,
    private val messageInteractor: MessageInteractor,
    private val uploadFileInteractor: UploadFileInteractor,
    private val chatsWithMessagesCommunication: ChatsWithMessagesCommunication,
    private val messagesCommunication: MessagesCommunication,
    private val messageCommunication: MessageCommunication,
    private val uploadFileCommunication: UploadFileCommunication,
    private val chatsWithMessagesMapper: ChatsWithMessagesDomainToUiMapper,
    private val messageMapper: MessageDomainToUiMapper,
    private val uploadFileMapper: UploadFileDomainToUiMapper,
    private val chatCache: Read<Triple<String, String, String>>,
    private val navigationCommunication: NavigationCommunication
) : BaseFileUploadViewModel(uploadFileInteractor, uploadFileMapper, uploadFileCommunication) {
    private var isBannedInChat = false
    private var selectedFileUri: Uri? = null

    fun getIsBannedInChat() = isBannedInChat
    fun setIsBannedInChat(isBanned: Boolean) { isBannedInChat = isBanned }
    fun getSelectedFileUri() = selectedFileUri
    fun setSelectedFileUri(uri: Uri?) { selectedFileUri = uri }

    fun fetchChatWithMessages(chatId: String) {
        chatsWithMessagesCommunication.map(ChatWithMessagesUi.Progress)
        viewModelScope.launch(Dispatchers.IO) {
            val chatWithMessages = chatsWithMessagesInteractor.fetchChatWithMessages(chatId)
            val chatWithMessagesUi = chatWithMessages.map(chatsWithMessagesMapper)
            withContext(Dispatchers.Main) {
                chatWithMessagesUi.map(chatsWithMessagesCommunication)
            }
        }
    }

    fun editChatSettings(chatId: String, userId: String, banned: Boolean, sendNotifications: Boolean) {
        chatsWithMessagesCommunication.map(ChatWithMessagesUi.Progress)
        viewModelScope.launch(Dispatchers.IO) {
            val chatWithMessages =
                chatsWithMessagesInteractor.editChatSettings(chatId, userId, banned, sendNotifications)
            val chatWithMessagesUi = chatWithMessages.map(chatsWithMessagesMapper)
            withContext(Dispatchers.Main) {
                chatWithMessagesUi.map(chatsWithMessagesCommunication)
            }
        }
    }

    fun leaveGroupChat(groupId: String, userId: String) {
        chatsWithMessagesCommunication.map(ChatWithMessagesUi.Progress)
        viewModelScope.launch(Dispatchers.IO) {
            val result = chatsWithMessagesInteractor.leaveGroupChat(groupId, userId)
            val resultUi = result.map(chatsWithMessagesMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(chatsWithMessagesCommunication)
            }
        }
    }

    fun deleteChat(chatId: String) {
        chatsWithMessagesCommunication.map(ChatWithMessagesUi.Progress)
        viewModelScope.launch(Dispatchers.IO) {
            val result = chatsWithMessagesInteractor.deleteChat(chatId)
            val resultUi = result.map(chatsWithMessagesMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(chatsWithMessagesCommunication)
            }
        }
    }

    fun fetchMessages(messages: List<Message>) = messagesCommunication.map(messages)

    fun sendDirectMessage(
        text: String, pathToFile: String, chatId: String, userId: String, pathUserId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = messageInteractor.sendDirectMessage(text, pathToFile, chatId, userId, pathUserId)
            val resultUi = resultDomain.map(messageMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(messageCommunication)
            }
        }
    }

    fun sendGroupMessage(
        text: String, pathToFile: String, chatId: String, userId: String, pathGroupId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = messageInteractor.sendGroupMessage(text, pathToFile, chatId, userId, pathGroupId)
            val resultUi = resultDomain.map(messageMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(messageCommunication)
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<ChatWithMessagesUi>) =
        chatsWithMessagesCommunication.observe(owner, observer)

    fun observeMessages(owner: LifecycleOwner, observer: Observer<List<Message>>) =
        messagesCommunication.observe(owner, observer)

    fun observeMessage(owner: LifecycleOwner, observer: Observer<MessageUi>) =
        messageCommunication.observe(owner, observer)

    fun showAddMember() = navigationCommunication.map(ADD_MEMBER_SCREEN)
    fun getUserToken() = chatsWithMessagesInteractor.getUserToken()
    fun getUserId() = chatsWithMessagesInteractor.getUserId()
    fun getChatId() = chatCache.read().first
    fun getChatTitle() = chatCache.read().second
    fun getCompanionId() = chatCache.read().third
}