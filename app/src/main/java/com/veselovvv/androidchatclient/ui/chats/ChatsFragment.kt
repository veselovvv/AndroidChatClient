package com.veselovvv.androidchatclient.ui.chats

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isEmpty
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ImageLoader
import com.veselovvv.androidchatclient.core.Retry
import com.veselovvv.androidchatclient.ui.core.BaseFragment
import com.veselovvv.androidchatclient.ui.login.LoginActivity
import com.veselovvv.androidchatclient.ui.login.Navigate
import com.veselovvv.androidchatclient.ui.user.HandleUserInfo
import de.hdodenhof.circleimageview.CircleImageView

class ChatsFragment : BaseFragment(R.layout.fragment_chats) {
    private lateinit var viewModel: ChatsViewModel
    private lateinit var imageLoader: ImageLoader
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var avatarCircleImageView: CircleImageView
    private lateinit var usernameTextView: MaterialTextView
    private lateinit var emailTextView: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = app.chatsViewModel
        imageLoader = app.imageLoader
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChatsAdapter(
            imageLoader,
            object : Retry {
                override fun tryAgain() = viewModel.fetchChats()
            },
            object : Navigate {
                override fun navigate() {
                    requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finish()
                }
            },
            object : ChatsAdapter.ChatListener {
                override fun showChat(id: String, title: String, companionId: String) =
                    viewModel.showChat(id, title, companionId)
            },
            viewModel.getUserToken()
        )

        drawerLayout = view.findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView = view.findViewById(R.id.navigation_view)
        avatarCircleImageView = navigationView.getHeaderView(0).findViewById(R.id.avatar_imageview_header)
        usernameTextView = navigationView.getHeaderView(0).findViewById(R.id.username_header)
        emailTextView =  navigationView.getHeaderView(0).findViewById(R.id.email_header)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_new_chat -> {
                    viewModel.showNewChat()
                    drawerLayout.close()
                    true
                }
                R.id.action_settings -> {
                    viewModel.showSettings()
                    drawerLayout.close()
                    true
                }
                R.id.action_ban_user -> {
                    viewModel.showBanUser()
                    drawerLayout.close()
                    true
                }
                else -> false
            }
        }

        toolbar = view.findViewById(R.id.toolbar_chats)
        toolbar.title = getString(R.string.app_title)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
        toolbar.setNavigationOnClickListener {
            drawerLayout.open()
            viewModel.observeUser(this) {
                it.map(object : HandleUserInfo {
                    override fun handle(
                        id: String,
                        name: String,
                        email: String,
                        password: String,
                        photoPathToFile: String,
                        role: String
                    ) {
                        if (photoPathToFile != "") imageLoader.load(
                            requireView(), photoPathToFile, viewModel.getUserToken(), avatarCircleImageView
                        )
                        usernameTextView.text = name
                        emailTextView.text = email

                        if (role == "ADMIN")
                            navigationView.showDrawerMenuItem(R.id.action_ban_user, true)
                        else navigationView.showDrawerMenuItem(R.id.action_ban_user, false)
                    }
                })
                it.map(requireView())
            }
            viewModel.fetchUser(viewModel.getUserId())
        }
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

private fun NavigationView.showDrawerMenuItem(@IdRes itemId: Int, showItem: Boolean) {
    menu.findItem(itemId).isVisible = showItem
}