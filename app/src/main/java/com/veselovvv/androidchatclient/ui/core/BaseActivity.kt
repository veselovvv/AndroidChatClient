package com.veselovvv.androidchatclient.ui.core

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {
    protected fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, addToStack: Boolean = true) {
        if (addToStack) supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
        else supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
}