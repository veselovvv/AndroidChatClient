package com.veselovvv.androidchatclient.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.domain.login.LoginDomainToUiMapper
import com.veselovvv.androidchatclient.domain.login.LoginInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val loginInteractor: LoginInteractor,
    private val mapper: LoginDomainToUiMapper,
    private val communication: LoginCommunication
) : ViewModel() {
    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = loginInteractor.login(email, password)
            val resultUi = resultDomain.map(mapper)
            withContext(Dispatchers.Main) {
                resultUi.map(communication)
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<LoginUi>) =
        communication.observe(owner, observer)
}