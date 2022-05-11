package com.veselovvv.androidchatclient.ui.newchat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesInteractor
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatWithMessagesUi
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatsWithMessagesCommunication
import com.veselovvv.androidchatclient.ui.user.UserCommunication
import com.veselovvv.androidchatclient.ui.user.UserUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewChatViewModel(
    private val userInteractor: UserInteractor, //TODO dry here and further, all that is used in Chats View Model
    private val userMapper: UsersDomainToUiMapper,
    private val userCommunication: UserCommunication,
    private val chatsWithMessagesInteractor: ChatsWithMessagesInteractor,
    private val chatsWithMessagesCommunication: ChatsWithMessagesCommunication,
    private val chatsWithMessagesMapper: ChatsWithMessagesDomainToUiMapper
) : ViewModel() {
    fun fetchUserByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.fetchUserByEmail(email)
            val resultUi = resultDomain.map(userMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(userCommunication)
            }
        }
    }

    fun createChat(title: String, createdBy: String, userIds: List<String>) {
        chatsWithMessagesCommunication.map(ChatWithMessagesUi.Progress)
        viewModelScope.launch(Dispatchers.IO) {
            val chatWithMessages = chatsWithMessagesInteractor.createChat(title, createdBy, userIds)
            val chatWithMessagesUi = chatWithMessages.map(chatsWithMessagesMapper)
            withContext(Dispatchers.Main) {
                chatWithMessagesUi.map(chatsWithMessagesCommunication)
            }
        }
    }

    fun observeUser(owner: LifecycleOwner, observer: Observer<UserUi>) = //TODO + rename to observe?
        userCommunication.observe(owner, observer)

    fun getUserId() = userInteractor.getUserId()
}