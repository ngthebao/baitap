package com.example.hoctap3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hoctap3.R
import com.example.hoctap3.adapter.ImageTheLoaiAdapter.OnClickItemListener

class ImageMonAnAdapter(val context: Context, var ds: List<String>, val clickListener: OnClickItemListener):RecyclerView.Adapter<ImageMonAnAdapter.ImageMonAnHolder>() {

    interface OnClickItemListener{
        fun onClickItem(position: Int)
    }
    inner class ImageMonAnHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imgMonan = itemView.findViewById<ImageView>(R.id.img_list)
        init {
            itemView.setOnClickListener {
                clickListener.onClickItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageMonAnHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item_monan, parent,false)
        return ImageMonAnHolder(view)
    }

    override fun onBindViewHolder(holder: ImageMonAnHolder, position: Int) {
        val imgURL = ds[position]
        Glide.with(context).load(imgURL).into(holder.imgMonan)
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}