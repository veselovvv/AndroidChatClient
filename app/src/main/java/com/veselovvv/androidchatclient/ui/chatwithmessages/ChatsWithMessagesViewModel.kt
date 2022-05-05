package com.veselovvv.androidchatclient.ui.chatwithmessages

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.core.Read
import com.veselovvv.androidchatclient.data.messages.Message
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesInteractor
import com.veselovvv.androidchatclient.domain.message.MessageDomainToUiMapper
import com.veselovvv.androidchatclient.domain.message.MessageInteractor
import com.veselovvv.androidchatclient.ui.message.MessageCommunication
import com.veselovvv.androidchatclient.ui.message.MessageUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatsWithMessagesViewModel(
    private val chatsWithMessagesInteractor: ChatsWithMessagesInteractor,
    private val messageInteractor: MessageInteractor,
    private val chatsWithMessagesCommunication: ChatsWithMessagesCommunication,
    private val messagesCommunication: MessagesCommunication,
    private val messageCommunication: MessageCommunication,
    private val chatsWithMessagesMapper: ChatsWithMessagesDomainToUiMapper,
    private val messageMapper: MessageDomainToUiMapper,
    private val chatCache: Read<Triple<String, String, String>>
) : ViewModel() {
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

    fun getUserId() = chatsWithMessagesInteractor.getUserId()
    fun getChatId() = chatCache.read().first
    fun getChatTitle() = chatCache.read().second
    fun getCompanionId() = chatCache.read().third
}