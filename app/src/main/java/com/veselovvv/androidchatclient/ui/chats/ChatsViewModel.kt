package com.veselovvv.androidchatclient.ui.chats

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.core.Save
import com.veselovvv.androidchatclient.domain.chats.ChatsDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chats.ChatsInteractor
import com.veselovvv.androidchatclient.ui.main.NavigationCommunication
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHATS_SCREEN
import com.veselovvv.androidchatclient.ui.main.Screens.Companion.CHAT_SCREEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatsViewModel(
    private val chatsInteractor: ChatsInteractor,
    private val mapper: ChatsDomainToUiMapper,
    private val communication: ChatsCommunication,
    private val chatCache: Save<Pair<String, String>>,
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

    fun observe(owner: LifecycleOwner, observer: Observer<List<ChatUi>>) =
        communication.observe(owner, observer)

    fun showChat(id: String, title: String) {
        chatCache.save(Pair(id, title))
        navigationCommunication.map(CHAT_SCREEN)
    }

    fun init() {
        navigator.save(CHATS_SCREEN)
        fetchChats()
    }
}