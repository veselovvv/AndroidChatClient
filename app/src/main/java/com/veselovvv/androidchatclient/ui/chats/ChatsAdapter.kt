package com.veselovvv.androidchatclient.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Retry
import com.veselovvv.androidchatclient.ui.login.Navigate
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

class ChatsAdapter(
    private val retry: Retry,
    private val navigate: Navigate,
    private val chatListener: ChatListener,
    private val userToken: String
) : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {
    private val chats = ArrayList<ChatUi>()

    fun update(new: List<ChatUi>) {
        chats.clear()
        chats.addAll(new)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) = when (chats[position]) {
        is ChatUi.Base -> 0
        is ChatUi.Fail -> 1
        is ChatUi.Progress -> 2
        is ChatUi.AuthFail -> 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> ChatsViewHolder.Base(R.layout.chat_layout.makeView(parent), chatListener)
        1 -> ChatsViewHolder.Fail(R.layout.fail_fullscreen.makeView(parent), retry)
        3 -> ChatsViewHolder.AuthFail(R.layout.fail_fullscreen.makeView(parent), navigate)
        else -> ChatsViewHolder.FullscreenProgress(R.layout.progress_fullscreen.makeView(parent))
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) =
        holder.bind(chats[position], userToken)
    override fun getItemCount() = chats.size

    abstract class ChatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(chat: ChatUi, userToken: String) {}

        class FullscreenProgress(view: View) : ChatsViewHolder(view)

        class Base(view: View, private val chatListener: ChatListener) : ChatsViewHolder(view) {
            private val titleTextView =
                itemView.findViewById<MaterialTextView>(R.id.title_text_view_chat_item)
            private val lastMessageTextView =
                itemView.findViewById<MaterialTextView>(R.id.last_message_text_view_chat_item)
            private val photoImageView =
                itemView.findViewById<CircleImageView>(R.id.avatar_imageview_chat_item)

            override fun bind(chat: ChatUi, userToken: String) {
                chat.map(object : ChatUi.BaseMapper {
                    override fun map(
                        id: UUID,
                        title: String,
                        lastMessageText: String,
                        lastMessagePathToFile: String,
                        photoPath: String
                    ) {
                        titleTextView.text = title
                        when {
                            lastMessageText != "" -> lastMessageTextView.text = lastMessageText
                            lastMessagePathToFile != "" ->
                                lastMessageTextView.text = itemView.context.getString(R.string.file)
                            else -> lastMessageTextView.text = itemView.context.getString(R.string.no_messages)
                        }

                        if (photoPath != "") {
                            //TODO DRY + path?
                            Glide.with(itemView)
                                .load(
                                    GlideUrl("http://10.0.2.2:8081/getFile/?path=" +
                                            photoPath.substringAfter("chat-server/"),
                                        LazyHeaders.Builder().addHeader("Authorization", userToken).build())
                                ).into(photoImageView)
                        } else photoImageView.setImageResource(R.drawable.ic_baseline_alternate_email_24)
                    }
                    override fun map(text: String) = Unit
                })

                itemView.setOnClickListener {
                    chat.open(chatListener)
                }
            }
        }

        class Fail(view: View, private val retry: Retry) : ChatsViewHolder(view) {
            private val message = itemView.findViewById<MaterialTextView>(R.id.error_message_text_view)
            private val button = itemView.findViewById<MaterialButton>(R.id.try_again_button)

            override fun bind(chat: ChatUi, userToken: String) {
                chat.map(object : ChatUi.BaseMapper {
                    override fun map(text: String) { message.text = text }
                    override fun map(
                        id: UUID,
                        title: String,
                        lastMessageText: String,
                        lastMessagePathToFile: String,
                        photoPath: String
                    ) = Unit
                })
                button.visibility = View.VISIBLE
                button.setOnClickListener { retry.tryAgain() }
            }
        }

        class AuthFail(view: View, private val navigate: Navigate) : ChatsViewHolder(view) {
            private val message = itemView.findViewById<MaterialTextView>(R.id.error_message_text_view)
            private val button = itemView.findViewById<MaterialButton>(R.id.login_button)

            override fun bind(chat: ChatUi, userToken: String) {
                chat.map(object : ChatUi.BaseMapper {
                    override fun map(text: String) { message.text = text }
                    override fun map(
                        id: UUID,
                        title: String,
                        lastMessageText: String,
                        lastMessagePathToFile: String,
                        photoPath: String
                    ) = Unit
                })
                button.visibility = View.VISIBLE
                button.setOnClickListener { navigate.navigate() }
            }
        }
    }

    interface ChatListener {
        fun showChat(id: UUID, title: String, companionId: String)
    }
}

private fun Int.makeView(parent: ViewGroup) =
    LayoutInflater.from(parent.context).inflate(this, parent, false)

