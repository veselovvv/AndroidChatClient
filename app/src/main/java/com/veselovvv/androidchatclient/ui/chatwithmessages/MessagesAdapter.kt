package com.veselovvv.androidchatclient.ui.chatwithmessages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ImageLoader
import com.veselovvv.androidchatclient.data.messages.Message
import de.hdodenhof.circleimageview.CircleImageView

class MessagesAdapter(
    private val imageLoader: ImageLoader, private val userId: String, private val userToken: String
) : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {
    private val messages = ArrayList<Message>()

    fun update(new: List<Message>) {
        messages.clear()
        messages.addAll(new)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MessagesViewHolder.Base(R.layout.message_layout.makeView(parent))

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) =
        holder.bind(messages[position], userId, userToken, imageLoader)

    override fun getItemCount() = messages.size

    abstract class MessagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(message: Message, userId: String, userToken: String, imageLoader: ImageLoader) = Unit

        class Base(view: View) : MessagesViewHolder(view) {
            private val sentMessageLayout =
                itemView.findViewById<ConstraintLayout>(R.id.sent_message_item)
            private val sentMessageTextTextView =
                itemView.findViewById<MaterialTextView>(R.id.text_text_view_sent_message_item)
            private val sentMessageDateTextView =
                itemView.findViewById<MaterialTextView>(R.id.date_text_view_sent_message_item)
            private val sentMessagePhotoImageView =
                itemView.findViewById<ShapeableImageView>(R.id.photo_image_view_sent_message_item)
            private val receivedMessageLayout =
                itemView.findViewById<ConstraintLayout>(R.id.received_message_item)
            private val receivedMessageNameTextView =
                itemView.findViewById<MaterialTextView>(R.id.name_text_view_received_message_item)
            private val receivedMessageTextTextView =
                itemView.findViewById<MaterialTextView>(R.id.text_text_view_received_message_item)
            private val receivedMessageDateTextView =
                itemView.findViewById<MaterialTextView>(R.id.date_text_view_received_message_item)
            private val receivedMessagePhotoImageView =
                itemView.findViewById<ShapeableImageView>(R.id.photo_image_view_received_message_item)
            private val receivedMessageAvatarImageView =
                itemView.findViewById<CircleImageView>(R.id.avatar_imageview_message_item)

            override fun bind(message: Message, userId: String, userToken: String, imageLoader: ImageLoader) {
                if (message.sender.userId.toString() == userId) {
                    receivedMessageLayout.visibility = View.GONE
                    receivedMessageAvatarImageView.visibility = View.GONE
                    sentMessageLayout.visibility = View.VISIBLE
                    sentMessageTextTextView.text = message.messageText
                    sentMessageDateTextView.text = message.dateTime.substring(11, 16)

                    if (message.messagePathToFile != "" && message.messagePathToFile != null) {
                        sentMessagePhotoImageView.visibility = View.VISIBLE
                        imageLoader.load(
                            itemView, message.messagePathToFile.toString(), userToken, sentMessagePhotoImageView
                        )
                    } else sentMessagePhotoImageView.visibility = View.GONE
                } else {
                    sentMessageLayout.visibility = View.GONE
                    receivedMessageLayout.visibility = View.VISIBLE
                    receivedMessageAvatarImageView.visibility = View.VISIBLE
                    receivedMessageNameTextView.text = message.sender.userName
                    receivedMessageTextTextView.text = message.messageText
                    receivedMessageDateTextView.text = message.dateTime.substring(11, 16)

                    if (message.sender.photoPath != "") imageLoader.load(
                        itemView, message.sender.photoPath, userToken, receivedMessageAvatarImageView
                    )

                    if (message.messagePathToFile != "" && message.messagePathToFile != null) {
                        receivedMessagePhotoImageView.visibility = View.VISIBLE
                        imageLoader.load(
                            itemView, message.messagePathToFile.toString(), userToken, receivedMessagePhotoImageView
                        )
                    } else receivedMessagePhotoImageView.visibility = View.GONE
                }
            }
        }
    }
}

private fun Int.makeView(parent: ViewGroup) =
    LayoutInflater.from(parent.context).inflate(this, parent, false)