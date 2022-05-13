package com.veselovvv.androidchatclient.ui.chatwithmessages

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ImageLoader
import com.veselovvv.androidchatclient.core.Retry
import com.veselovvv.androidchatclient.data.chatdetails.ChatDetails
import com.veselovvv.androidchatclient.data.messages.Message
import com.veselovvv.androidchatclient.ui.core.BaseFileUploadFragment
import com.veselovvv.androidchatclient.ui.fileuploading.SetPathToFile
import kotlinx.coroutines.*
import okhttp3.*
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class ChatWithMessagesFragment : BaseFileUploadFragment(R.layout.fragment_chat_with_messages) {
    private lateinit var viewModel: ChatsWithMessagesViewModel
    private lateinit var imageLoader: ImageLoader
    private lateinit var toolbar: Toolbar
    private lateinit var progressLayout: FrameLayout
    private lateinit var failLayout: LinearLayout
    private lateinit var messageTextView: MaterialTextView
    private lateinit var tryAgainButton: MaterialButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var enterMessageEditText: TextInputEditText
    private lateinit var sendMessageImageView: ShapeableImageView
    private lateinit var attachFileImageView: ShapeableImageView
    private lateinit var fileSelectedLayout: LinearLayout
    private lateinit var unselectFileButton: ShapeableImageView
    private lateinit var stompClient: StompClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = app.chatsWithMessagesViewModel
        imageLoader = app.imageLoader
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MessagesAdapter(imageLoader, viewModel.getUserId(), viewModel.getUserToken())
        recyclerView = view.findViewById(R.id.recycler_view_chat_with_messages)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.itemCount - 1)

        toolbar = view.findViewById(R.id.toolbar_chat_with_messages)
        toolbar.title = viewModel.getChatTitle()
        toolbar.inflateMenu(R.menu.chat_with_messages_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_enable_notifications -> {
                    viewModel.editChatSettings(
                        viewModel.getChatId(), viewModel.getUserId(), viewModel.getIsBannedInChat(), true
                    )
                    showSnackBar(R.string.notifications_enabled)
                    true
                }
                R.id.action_disable_notifications -> {
                    viewModel.editChatSettings(
                        viewModel.getChatId(), viewModel.getUserId(), viewModel.getIsBannedInChat(), false
                    )
                    showSnackBar(R.string.notifications_disabled)
                    true
                }
                R.id.action_delete_chat -> {
                    viewModel.deleteChat(viewModel.getChatId())
                    showSnackBar(R.string.chat_is_deleted)
                    requireActivity().onBackPressed()
                    true
                }
                R.id.action_leave_chat -> {
                    viewModel.leaveGroupChat(viewModel.getChatId(), viewModel.getUserId())
                    showSnackBar(R.string.you_have_left_the_chat)
                    requireActivity().onBackPressed()
                    true
                }
                R.id.action_add_member -> {
                    viewModel.showAddMember()
                    true
                }
                else -> false
            }
        }
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        progressLayout = requireActivity().findViewById(R.id.progress_layout)
        enterMessageEditText = requireActivity().findViewById(R.id.enter_message_chat_with_messages)
        sendMessageImageView = requireActivity().findViewById(R.id.send_button_chat_with_messages)
        attachFileImageView = requireActivity().findViewById(R.id.attach_file_chat_with_messages)
        fileSelectedLayout = requireActivity().findViewById(R.id.file_selected_layout_chat_with_messages)
        failLayout = requireActivity().findViewById(R.id.fail_layout)
        messageTextView = requireActivity().findViewById(R.id.message_text_view_chat_with_messages)
        tryAgainButton = requireActivity().findViewById(R.id.try_again_button_chat_with_messages)
        unselectFileButton = requireActivity().findViewById(R.id.unselect_file_button_chat_with_messages)

        //TODO move to ViewModel?
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8081/chat-ws/websocket")
        stompClient.connect()

        val destination = if (viewModel.getCompanionId() != "")
            "/user/${viewModel.getUserId()}/queue/messages"
        else "/group/${viewModel.getChatId()}"

        stompClient.topic(destination).subscribe {
            MainScope().launch(Dispatchers.Main) {
                fetchData(adapter)
            }
        }

        fetchData(adapter)

        attachFileImageView.setOnClickListener { getPermission() }
        sendMessageImageView.setOnClickListener {
            var set = true
            if (viewModel.getSelectedFileUri() != null) {
                viewModel.observeFileUploading(this) {
                    it.map(object : SetPathToFile {
                        override fun setPath(filePath: String) {
                            if (set) {
                                viewModel.setPathToFile(filePath)
                                set = !set
                            }
                        }
                    })
                    it.map(requireView())
                }
                viewModel.uploadFile(viewModel.getSelectedFileUri()!!)
                viewModel.setSelectedFileUri(null)
            }

            viewModel.observeMessage(this) {
                it.map()
                it.map(requireView())
            }

            if (enterMessageEditText.text.toString() != "" || viewModel.getPathToFile() != "") {
                if ((viewModel.getCompanionId() != "")) {
                    viewModel.sendDirectMessage(
                        enterMessageEditText.text.toString(),
                        viewModel.getPathToFile(),
                        viewModel.getChatId(),
                        viewModel.getUserId(),
                        viewModel.getCompanionId()
                    )
                    MainScope().launch(Dispatchers.Main) {
                        delay(500)
                        fetchData(adapter)
                    }
                } else {
                    viewModel.sendGroupMessage(
                        enterMessageEditText.text.toString(),
                        viewModel.getPathToFile(),
                        viewModel.getChatId(),
                        viewModel.getUserId(),
                        viewModel.getChatId()
                    )
                }
            } else showSnackBar(R.string.message_is_empty)
            viewModel.setPathToFile("")
            enterMessageEditText.setText("")
            fileSelectedLayout.visibility = View.GONE
        }
    }

    override fun doOnActivityResult(data: Uri) {
        viewModel.setSelectedFileUri(data)
        fileSelectedLayout.visibility = View.VISIBLE

        unselectFileButton.setOnClickListener {
            viewModel.setSelectedFileUri(null)
            fileSelectedLayout.visibility = View.GONE
        }
    }

    private fun fetchData(adapter: MessagesAdapter) {
        viewModel.observe(this) {
            it.map(progressLayout)
            it.map(object : HideMenuItems {
                override fun hide(chat: ChatDetails) {
                    chat.chatSettingsList.forEach { chatSettings ->
                        if (chatSettings.userId.toString() == viewModel.getUserId()) {
                            viewModel.setIsBannedInChat(chatSettings.isBanned)
                            if (chatSettings.isNotificationsEnabled) {
                                toolbar.showMenuItem(R.id.action_enable_notifications, false)
                                toolbar.showMenuItem(R.id.action_disable_notifications, true)
                            } else {
                                toolbar.showMenuItem(R.id.action_disable_notifications, false)
                                toolbar.showMenuItem(R.id.action_enable_notifications, true)
                            }
                        }
                    }

                    if (viewModel.getCompanionId() != "") {
                        toolbar.showMenuItem(R.id.action_leave_chat, false)
                        toolbar.showMenuItem(R.id.action_add_member, false)
                    } else {
                        if (viewModel.getUserId() != chat.createdByUserId.toString()) {
                            toolbar.showMenuItem(R.id.action_delete_chat, false)
                            toolbar.showMenuItem(R.id.action_add_member, false)
                        } else toolbar.showMenuItem(R.id.action_leave_chat, false)
                    }
                }
            })
            it.map(object : HandleMessages {
                override fun fetchMessages(messages: List<Message>) {
                    viewModel.observeMessages(this@ChatWithMessagesFragment, { messageList ->
                        adapter.update(messageList) })
                    viewModel.fetchMessages(messages)
                }
            })
            it.map(failLayout, messageTextView, tryAgainButton, object : Retry {
                    override fun tryAgain() = viewModel.fetchChatWithMessages(viewModel.getChatId())
                }
            )
        }
        viewModel.fetchChatWithMessages(viewModel.getChatId())
        recyclerView.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        stompClient.disconnect() //TODO
    }
}

private fun Toolbar.showMenuItem(@IdRes itemId: Int, showItem: Boolean) {
    menu.findItem(itemId).isVisible = showItem
}