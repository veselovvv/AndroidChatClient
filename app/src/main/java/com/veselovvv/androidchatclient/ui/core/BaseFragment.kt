package com.veselovvv.androidchatclient.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.veselovvv.androidchatclient.core.ChatApp

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment() {
    protected lateinit var app: ChatApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = requireActivity().application as ChatApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout, container, false)

    protected fun setupToolbar(toolbar: Toolbar, @StringRes title: Int) {
        toolbar.title = getString(title)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    protected fun showSnackBar(@StringRes text: Int) =
        Snackbar.make(requireView(), getString(text), Snackbar.LENGTH_SHORT).show()
}