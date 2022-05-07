package com.veselovvv.androidchatclient.ui.chatwithmessages

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.core.Retry
import com.veselovvv.androidchatclient.data.messages.Message
import com.veselovvv.androidchatclient.ui.fileuploading.SetPathToFile
import kotlinx.coroutines.*
import okhttp3.*
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class ChatWithMessagesFragment : Fragment() {
    private lateinit var viewModel: ChatsWithMessagesViewModel
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

    private companion object {
        const val READ_EXTERNAL_REQUEST = 1
        const val FILE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).chatsWithMessagesViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_with_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MessagesAdapter(viewModel.getUserId(), viewModel.getUserToken())
        recyclerView = view.findViewById(R.id.recycler_view_chat_with_messages)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.itemCount - 1)

        toolbar = view.findViewById(R.id.toolbar_chat_with_messages)
        toolbar.title = viewModel.getChatTitle()
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

        attachFileImageView.setOnClickListener {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_REQUEST)
            else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "*/*"
                startActivityForResult(intent, FILE_REQUEST_CODE)
            }
        }

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
            //TODO if text is empty and file
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
            } else Snackbar.make(requireView(), getString(R.string.message_is_empty), Snackbar.LENGTH_SHORT).show()
            viewModel.setPathToFile("")
            enterMessageEditText.setText("")
            fileSelectedLayout.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data?.data != null) {
            viewModel.setSelectedFileUri(data.data)
            fileSelectedLayout.visibility = View.VISIBLE

            unselectFileButton.setOnClickListener {
                viewModel.setSelectedFileUri(null)
                fileSelectedLayout.visibility = View.GONE
            }
        }
    }

    private fun fetchData(adapter: MessagesAdapter) {
        viewModel.observe(this) {
            it.map(progressLayout)
            it.map(object : HandleMessages {
                override fun fetchMessages(messages: List<Message>) {
                    viewModel.observeMessages(this@ChatWithMessagesFragment, { messageList ->
                        adapter.update(messageList) })
                    viewModel.fetchMessages(messages)
                }
            })
            it.map(
                failLayout, messageTextView, tryAgainButton, object : Retry {
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