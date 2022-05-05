package com.veselovvv.androidchatclient.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.veselovvv.androidchatclient.core.Read

class MainViewModel(
    private val navigator: Read<Int>,
    private val communication: NavigationCommunication
) : ViewModel() {
    fun init() {
        communication.map(navigator.read())
    }

    fun observe(owner: LifecycleOwner, observer: Observer<Int>) =
        communication.observe(owner, observer)

    fun navigateBack(): Boolean {
        val currentScreen = navigator.read()
        val exit = currentScreen == 0

        if (!exit) {
            val newScreen = currentScreen - 1
            communication.map(newScreen)
        }
        return exit
    }
}