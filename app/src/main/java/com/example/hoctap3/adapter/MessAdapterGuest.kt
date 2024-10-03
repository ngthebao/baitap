package com.example.hoctap3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.R
import com.example.hoctap3.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessAdapterGuest(
    private val messages: List<Message>,
    private val currentUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_GUEST = 1
    private val VIEW_TYPE_ADMIN = 2

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].userId == currentUserId) {
            VIEW_TYPE_GUEST
        } else {
            VIEW_TYPE_ADMIN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_GUEST) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mess_guest, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mess_admin, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message_sent)
        private val timeText: TextView = itemView.findViewById(R.id.text_time_sent)

        fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(message.timestamp))
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameText: TextView = itemView.findViewById(R.id.text_user_name)
        private val messageText: TextView = itemView.findViewById(R.id.text_message_received)
        private val timeText: TextView = itemView.findViewById(R.id.text_time_received)

        fun bind(message: Message) {
            userNameText.text = message.userName
            messageText.text = message.message
            timeText.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(message.timestamp))
        }
    }
}