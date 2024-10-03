package com.example.hoctap3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hoctap3.interfaceClick.ItemClickMonAnGuest
import com.example.hoctap3.R
import com.example.hoctap3.model.MonAn

class MonAnGuestAdapter(var ds : MutableList<MonAn>, private val listener: ItemClickMonAnGuest) : RecyclerView.Adapter<MonAnGuestAdapter.MonAnViewHolder>(){
    inner class MonAnViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tenMonAn = itemView.findViewById<TextView>(R.id.txt_ten_monan_home)
        var tenTheLoai = itemView.findViewById<TextView>(R.id.txt_ten_theloai_home)
        var giaMonAn = itemView.findViewById<TextView>(R.id.txt_gia_monan_home)
        var imgMonan = itemView.findViewById<ImageView>(R.id.img_monan_home)
        val btnCart = itemView.findViewById<ImageButton>(R.id.imgbtn_cart_monan)
        val btnFavorite = itemView.findViewById<ImageButton>(R.id.imgbtn_favorite_monan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonAnViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_monan_guest, parent, false)
        return MonAnViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonAnViewHolder, position: Int) {
        holder.itemView.apply {
            val monan = ds[position]
            holder.tenMonAn.text = monan.tenMonan
            holder.tenTheLoai.text = monan.tenTheLoai
            holder.giaMonAn.text = monan.gia
            Glide.with(holder.itemView.context).load(monan.imageMonan).into(holder.imgMonan)
            holder.btnCart.setOnClickListener {
                listener.monanCart(position)
            }
            holder.btnFavorite.setOnClickListener {
                listener.monanFavorite(position)
            }
        }
    }

    override fun getItemCount(): Int {
       return ds.size
    }
}