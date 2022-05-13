package com.veselovvv.androidchatclient.ui.user

import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.core.BaseFetchUserByEmailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BanUserViewModel(
    private val userInteractor: UserInteractor,
    private val userMapper: UsersDomainToUiMapper,
    private val userCommunication: UserCommunication
) : BaseFetchUserByEmailViewModel(userInteractor, userMapper, userCommunication) {
    fun banUser(userId: String, banned: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = userInteractor.banUser(userId, banned)
            val resultUi = resultDomain.map(userMapper)
            withContext(Dispatchers.Main) {
                resultUi.map(userCommunication)
            }
        }
    }
}