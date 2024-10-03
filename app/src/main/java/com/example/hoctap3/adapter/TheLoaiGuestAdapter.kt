package com.example.hoctap3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hoctap3.R
import com.example.hoctap3.model.TheLoai

class TheLoaiGuestAdapter(var ds : MutableList<TheLoai>) : RecyclerView.Adapter<TheLoaiGuestAdapter.TheLoaiViewHolder>(){
    class TheLoaiViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tenTheLoai = itemView.findViewById<TextView>(R.id.txt_theloai_home)
        val imgTheLoai = itemView.findViewById<ImageView>(R.id.img_theloai_home)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheLoaiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_theloai_home,parent,false)
        return TheLoaiViewHolder(view)
    }

    override fun onBindViewHolder(holder: TheLoaiViewHolder, position: Int) {
        holder.itemView.apply {
            holder.tenTheLoai.text = ds[position].tenTheLoai
            val imgURL = ds[position]
            Glide.with(holder.itemView.context).load(imgURL.imgTheLoai).into(holder.imgTheLoai)
            holder.imgTheLoai.setOnClickListener {
            }
        }

    }

    override fun getItemCount(): Int {
        return ds.size
    }
}