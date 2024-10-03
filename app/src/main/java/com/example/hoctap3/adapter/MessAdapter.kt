package com.example.hoctap3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.R
import com.example.hoctap3.adapter.DonHangGuestAdapter.OnClickItemListener
import com.example.hoctap3.model.Message

class MessAdapter(private val messageList: List<Message>,  val clickListener : OnClickItemListener) : RecyclerView.Adapter<MessAdapter.ViewHolder>() {
    interface OnClickItemListener {
        fun onClickItem(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tenMess = itemView.findViewById<TextView>(R.id.txt_ten_taikhoan_phanhoi)
        init {
            itemView.setOnClickListener {
                clickListener.onClickItem(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_phanhoi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.tenMess.text = messageList[position].userName
        }
    }
    override fun getItemCount(): Int {
        return messageList.size
    }
}