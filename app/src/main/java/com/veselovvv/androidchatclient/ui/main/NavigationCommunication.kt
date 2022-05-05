package com.veselovvv.androidchatclient.ui.main

import com.veselovvv.androidchatclient.core.Communication

interface NavigationCommunication : Communication<Int> {
    class Base : Communication.Base<Int>(), NavigationCommunication
}