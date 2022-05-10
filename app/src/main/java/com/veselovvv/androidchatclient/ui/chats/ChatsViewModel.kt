package com.veselovvv.androidchatclient.ui.chats

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.core.Save
import com.veselovvv.androidchatclient.domain.chats.ChatsDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chats.ChatsInteractor
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.main.NavigationCommunication
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHATS_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHAT_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.SETTINGS_SCREEN
import com.veselovvv.androidchatclient.ui.user.UserCommunication
import com.veselovvv.androidchatclient.ui.user.UserUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatsViewModel(
    private val chatsInteractor: ChatsInteractor,
    private val userInteractor: UserInteractor,
    private val mapper: ChatsDomainToUiMapper,
    private val userMapper: UsersDomainToUiMapper,
    private val communication: ChatsCommunication,
    private val userCommunication: UserCommunication,
    private val chatCache: Save<Triple<String, String, String>>,
    private val navigator: Save<Int>,
    private val navigationCommunication: NavigationCommunication
) : ViewModel() {
    fun fetchChats() = getChats(null)
    fun searchChats(query: String) = getChats(query)

    private fun getChats(query: String?) {
        communication.map(listOf(ChatUi.Progress))
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain =
                if (query == null) chatsInteractor.fetchChats() else chatsInteractor.searchChats(query)
            val resultUi = resultDomain.map(mapper)
            withContext(Dispatchers.Main) {
                resultUi.map(communication)
            }
        }
    }

    fun fetchUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.fetchUser(userId)
            val resultUi = resultDomain.map(userMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(userCommunication)
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<ChatUi>>) =
        communication.observe(owner, observer)

    fun observeUser(owner: LifecycleOwner, observer: Observer<UserUi>) =
        userCommunication.observe(owner, observer)

    fun showChat(id: String, title: String, companionId: String) {
        chatCache.save(Triple(id, title, companionId))
        navigationCommunication.map(CHAT_SCREEN)
    }

    fun showSettings() {
        navigationCommunication.map(SETTINGS_SCREEN)
    }

    fun getUserId() = userInteractor.getUserId()
    fun getUserToken() = userInteractor.getUserToken()

    fun init() {
        navigator.save(CHATS_SCREEN)
        fetchChats()
    }
}