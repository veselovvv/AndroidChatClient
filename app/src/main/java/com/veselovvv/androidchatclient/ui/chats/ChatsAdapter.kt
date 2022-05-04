package com.veselovvv.androidchatclient.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.Retry
import java.util.*
import kotlin.collections.ArrayList

class ChatsAdapter(
    private val retry: Retry,
    private val chatListener: ChatListener
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> ChatsViewHolder.Base(R.layout.chat_layout.makeView(parent), chatListener)
        1 -> ChatsViewHolder.Fail(R.layout.fail_fullscreen.makeView(parent), retry)
        else -> ChatsViewHolder.FullscreenProgress(R.layout.progress_fullscreen.makeView(parent))
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) = holder.bind(chats[position])
    override fun getItemCount() = chats.size

    abstract class ChatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(chat: ChatUi) {}

        class FullscreenProgress(view: View) : ChatsViewHolder(view)

        class Base(view: View, private val chatListener: ChatListener) : ChatsViewHolder(view) {
            private val titleTextView = itemView.findViewById<MaterialTextView>(R.id.title_text_view_chat_item)

            override fun bind(chat: ChatUi) {
                chat.map(object : ChatUi.BaseMapper {
                    override fun map(id: UUID, title: String) {
                        titleTextView.text = title
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

            override fun bind(chat: ChatUi) {
                chat.map(object : ChatUi.BaseMapper {
                    override fun map(text: String) {
                        message.text = text
                    }
                    override fun map(id: UUID, title: String) = Unit
                })
                button.setOnClickListener { retry.tryAgain() }
            }
        }
    }

    interface ChatListener {
        fun showChat(id: UUID, title: String)
    }
}

private fun Int.makeView(parent: ViewGroup) =
    LayoutInflater.from(parent.context).inflate(this, parent, false)

