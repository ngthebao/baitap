package com.example.hoctap3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hoctap3.R
import com.example.hoctap3.model.Cart
import com.example.hoctap3.model.MonAn

class CartGuestAdapter(var ds : MutableList<MonAn>) : RecyclerView.Adapter<CartGuestAdapter.MonAnCartViewHolder>(){
    inner class MonAnCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tenMonAn = itemView.findViewById<TextView>(R.id.txt_ten_monan_cart)
        var tenTheLoai = itemView.findViewById<TextView>(R.id.txt_ten_theloai_cart)
        var giaMonAn = itemView.findViewById<TextView>(R.id.txt_gia_monan_cart)
        var imgMonan = itemView.findViewById<ImageView>(R.id.img_monan_cart)
        val imgBtnDelete = itemView.findViewById<ImageButton>(R.id.imgbtn_cart_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonAnCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_fragment, parent, false)
        return MonAnCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonAnCartViewHolder, position: Int) {
        holder.itemView.apply {
            val monan = ds[position]
            holder.tenMonAn.text = monan.tenMonan
            holder.tenTheLoai.text = monan.tenTheLoai
            holder.giaMonAn.text = monan.gia
            Glide.with(holder.itemView.context).load(monan.imageMonan).into(holder.imgMonan)
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }

}