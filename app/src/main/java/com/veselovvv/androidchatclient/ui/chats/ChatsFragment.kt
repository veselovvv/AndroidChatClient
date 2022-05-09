package com.veselovvv.androidchatclient.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.core.Retry
import java.util.*

class ChatsFragment : Fragment() {
    private lateinit var viewModel: ChatsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).chatsViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_chats, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChatsAdapter(
            object : Retry {
                override fun tryAgain() = viewModel.fetchChats()
            },
            object : ChatsAdapter.ChatListener {
                override fun showChat(id: UUID, title: String, companionId: String) =
                    viewModel.showChat(id.toString(), title, companionId) //TODO id
            }
        )

        toolbar = view.findViewById(R.id.toolbar_chats)
        toolbar.title = getString(R.string.app_title)
        toolbar.inflateMenu(R.menu.chats_menu)
        toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.action_search_chats -> {
                    val searchView = it.actionView as SearchView
                    searchView.queryHint = getString(R.string.search_chats_hint)
                    searchView.setOnQueryTextListener(SimpleQueryListener(viewModel))
                    true
                }
                else -> false
            }
        }

        swipeToRefreshLayout = view.findViewById(R.id.swipe_to_refresh_chats)
        swipeToRefreshLayout.setOnRefreshListener {
            viewModel.observe(this, { adapter.update(it) })
            viewModel.init()
            swipeToRefreshLayout.isRefreshing = false
        }

        recyclerView = view.findViewById(R.id.recycler_view_chats)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        viewModel.observe(this, { adapter.update(it) })
        viewModel.init()
    }

    override fun onStart() {
        super.onStart()
        if (toolbar.menu.isEmpty()) toolbar.inflateMenu(R.menu.chats_menu)
    }

    override fun onPause() {
        super.onPause()
        toolbar.menu.clear()
    }
}