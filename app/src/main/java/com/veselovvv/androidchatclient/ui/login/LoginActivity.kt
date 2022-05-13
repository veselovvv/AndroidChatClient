package com.veselovvv.androidchatclient.ui.login

import android.os.Bundle
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.ui.core.BaseActivity

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        replaceFragment(R.id.container_login, LoginFragment(), false)
    }
}