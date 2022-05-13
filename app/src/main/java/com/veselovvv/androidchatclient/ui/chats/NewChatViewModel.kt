package com.veselovvv.androidchatclient.ui.chats

import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesInteractor
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatWithMessagesUi
import com.veselovvv.androidchatclient.ui.chatwithmessages.ChatsWithMessagesCommunication
import com.veselovvv.androidchatclient.ui.core.BaseFetchUserByEmailViewModel
import com.veselovvv.androidchatclient.ui.user.UserCommunication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewChatViewModel(
    private val userInteractor: UserInteractor,
    private val userMapper: UsersDomainToUiMapper,
    private val userCommunication: UserCommunication,
    private val chatsWithMessagesInteractor: ChatsWithMessagesInteractor,
    private val chatsWithMessagesCommunication: ChatsWithMessagesCommunication,
    private val chatsWithMessagesMapper: ChatsWithMessagesDomainToUiMapper
) : BaseFetchUserByEmailViewModel(userInteractor, userMapper, userCommunication) {
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

    fun getUserId() = userInteractor.getUserId()
}