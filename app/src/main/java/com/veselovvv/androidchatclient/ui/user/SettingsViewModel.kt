package com.veselovvv.androidchatclient.ui.user

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val userInteractor: UserInteractor, //TODO dry here and further, all that is used in Chats View Model
    private val userMapper: UsersDomainToUiMapper,
    private val userCommunication: UserCommunication
) : ViewModel() {
    fun fetchUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.fetchUser(userId)
            val resultUi = resultDomain.map(userMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(userCommunication)
            }
        }
    }

    fun observeUser(owner: LifecycleOwner, observer: Observer<UserUi>) = //TODO + rename to observe?
        userCommunication.observe(owner, observer)

    fun getUserId() = userInteractor.getUserId()
    fun getUserToken() = userInteractor.getUserToken()
}