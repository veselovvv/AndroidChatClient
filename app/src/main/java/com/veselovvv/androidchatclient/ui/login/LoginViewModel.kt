package com.veselovvv.androidchatclient.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.veselovvv.androidchatclient.core.Save
import com.veselovvv.androidchatclient.domain.login.LoginDomainToUiMapper
import com.veselovvv.androidchatclient.domain.login.LoginInteractor
import com.veselovvv.androidchatclient.ui.core.BaseViewModel
import com.veselovvv.androidchatclient.ui.main.NavigationCommunication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val loginInteractor: LoginInteractor,
    private val mapper: LoginDomainToUiMapper,
    private val communication: LoginCommunication,
    private val navigator: Save<Int>,
    private val navigationCommunication: NavigationCommunication,
    private val validator: Validator,
) : BaseViewModel(validator) {
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

    fun init(email: String, password: String) { //TODO
        //navigator.save(Screens.LOGIN_SCREEN)
        login(email, password)
    }
}