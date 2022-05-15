package com.veselovvv.androidchatclient.sl

import androidx.lifecycle.ViewModel

interface BaseModule<VM : ViewModel> {
    fun getViewModel(): VM
}