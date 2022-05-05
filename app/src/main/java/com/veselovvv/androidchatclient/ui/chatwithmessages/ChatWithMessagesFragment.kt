package com.veselovvv.androidchatclient.ui.chatwithmessages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.core.Retry
import com.veselovvv.androidchatclient.data.messages.Message
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
    private lateinit var stompClient: StompClient

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

        val adapter = MessagesAdapter(viewModel.getUserId())
        recyclerView = view.findViewById(R.id.recycler_view_chat_with_messages)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.itemCount - 1)

        toolbar = view.findViewById(R.id.toolbar_chat_with_messages)
        toolbar.title = viewModel.getChatTitle()
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        progressLayout = requireActivity().findViewById(R.id.progress_layout)
        enterMessageEditText = requireActivity().findViewById(R.id.enter_message_chat_with_messages)
        sendMessageImageView = requireActivity().findViewById(R.id.send_button_chat_with_messages)
        failLayout = requireActivity().findViewById(R.id.fail_layout)
        messageTextView = requireActivity().findViewById(R.id.message_text_view_chat_with_messages)
        tryAgainButton = requireActivity().findViewById(R.id.try_again_button_chat_with_messages)

        //TODO + move to ViewModel?
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8081/chat-ws/websocket")
        stompClient.connect()
        //TODO get userId
        val destination = if (viewModel.getCompanionId() != "") {
            Log.d("LogTag", "Direct, companionId: ${viewModel.getCompanionId()}") //TODO
            "/user/${viewModel.getUserId()}/queue/messages" // OR COMPANION???
        } else {
            Log.d("LogTag", "Group") //TODO
            "/group/${viewModel.getChatId()}"
        }
        stompClient.topic(destination).subscribe {
            MainScope().launch(Dispatchers.Main) {
                Log.d("LogTag", "destination: $destination") //TODO
                fetchData(adapter)
            }
        }

        fetchData(adapter)

        //TODO send and receive messages via web-sockets, update UI after message is sent?
        sendMessageImageView.setOnClickListener {
            viewModel.observeMessage(this) {
                it.map()
                it.map(requireView())
            }
            //TODO if text is empty + TODO path to file + get recepient id
            if ((viewModel.getCompanionId() != "")) {
                viewModel.sendDirectMessage(
                    enterMessageEditText.text.toString(),
                    "",
                    viewModel.getChatId(),
                    viewModel.getUserId(),
                    viewModel.getCompanionId() //TODO
                )
                //TODO + in group chat like that?, when get something add message in ui, not update ui?
                /*MainScope().launch(Dispatchers.IO) {
                    stompClient.send("/user/${viewModel.getCompanionId()}/queue/messages", enterMessageEditText.text.toString())
                    Log.d("LogTag", "Send to ${viewModel.getCompanionId()}")
                }*/
                MainScope().launch(Dispatchers.Main) {
                    delay(500)
                    fetchData(adapter)
                }
            }
            else {
                viewModel.sendGroupMessage(
                    enterMessageEditText.text.toString(),
                    "",
                    viewModel.getChatId(),
                    viewModel.getUserId(),
                    viewModel.getChatId()
                )
            }
            enterMessageEditText.setText("")
        }
    }

    private fun fetchData(adapter: MessagesAdapter) {
        viewModel.observe(this) {
            it.map(progressLayout)
            it.map(object : HandleMessages {
                override fun fetchMessages(messages: List<Message>) {
                    viewModel.observeMessages(this@ChatWithMessagesFragment, { messageList ->
                        adapter.update(messageList.sortedBy { message -> message.dateTime }) })
                    viewModel.fetchMessages(messages)
                }
            })
            it.map(
                failLayout, messageTextView, tryAgainButton,
                object : Retry {
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