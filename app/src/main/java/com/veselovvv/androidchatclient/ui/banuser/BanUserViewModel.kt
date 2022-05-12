package com.veselovvv.androidchatclient.ui.banuser

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.user.UserCommunication
import com.veselovvv.androidchatclient.ui.user.UserUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BanUserViewModel(
    private val userInteractor: UserInteractor, //TODO dry here and further, all that is used in Chats View Model
    private val userMapper: UsersDomainToUiMapper,
    private val userCommunication: UserCommunication
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

    fun banUser(userId: String, banned: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.banUser(userId, banned)
            val resultUi = resultDomain.map(userMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(userCommunication)
            }
        }
    }

    fun observeUser(owner: LifecycleOwner, observer: Observer<UserUi>) = //TODO + rename to observe?
        userCommunication.observe(owner, observer)
}