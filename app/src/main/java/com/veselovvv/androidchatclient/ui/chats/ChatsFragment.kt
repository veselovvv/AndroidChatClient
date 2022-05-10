package com.veselovvv.androidchatclient.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isEmpty
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.core.Retry
import com.veselovvv.androidchatclient.ui.user.HandleUserInfo
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class ChatsFragment : Fragment() {
    private lateinit var viewModel: ChatsViewModel
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
                R.id.action_new_group -> {
                    //TODO
                    Snackbar.make(requireView(), "New Group", Snackbar.LENGTH_SHORT).show()
                    drawerLayout.close()
                    true
                }
                R.id.action_people -> {
                    //TODO
                    Snackbar.make(requireView(), "People", Snackbar.LENGTH_SHORT).show()
                    drawerLayout.close()
                    true
                }
                R.id.action_settings -> {
                    //TODO
                    Snackbar.make(requireView(), "Settings", Snackbar.LENGTH_SHORT).show()
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
                        id: String, name: String, email: String, password: String, photoPathToFile: String
                    ) {
                        //TODO path?
                        Glide.with(requireView())
                            .load(
                                GlideUrl("http://10.0.2.2:8081/getFile/?path=" +
                                        photoPathToFile.substringAfter("chat-server/"),
                                LazyHeaders.Builder().addHeader("Authorization", viewModel.getUserToken()
                                ).build())
                            ).into(avatarCircleImageView)
                        usernameTextView.text = name
                        emailTextView.text = email
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