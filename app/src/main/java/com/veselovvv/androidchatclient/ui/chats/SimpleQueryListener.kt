package com.veselovvv.androidchatclient.ui.chats

import androidx.appcompat.widget.SearchView

class SimpleQueryListener(private val viewModel: ChatsViewModel) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = find(query)
    override fun onQueryTextChange(newText: String?) = find(newText)

    private fun find(query: String?): Boolean {
        viewModel.searchChats(query.toString())
        return !query.isNullOrEmpty()
    }
}