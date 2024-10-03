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
import com.example.hoctap3.adapter.ImageTheLoaiAdapter.OnClickItemListener
import com.example.hoctap3.model.Cart
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.OrderAdmin
import com.example.hoctap3.model.User

class DonHangGuestAdapter(var ds : List<OrderAdmin>,  val clickListener: OnClickItemListener) : RecyclerView.Adapter<DonHangGuestAdapter.MonAnCartViewHolder>(){
    interface OnClickItemListener{
        fun onClickItem(position: Int)
    }
    inner class MonAnCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tenTaiKhoan = itemView.findViewById<TextView>(R.id.txt_ten_taikhoan)
        init {
            itemView.setOnClickListener {
                clickListener.onClickItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonAnCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_donhang, parent, false)
        return MonAnCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonAnCartViewHolder, position: Int) {
        holder.itemView.apply {
            holder.tenTaiKhoan.text = ds[position].userName
        }
    }
    override fun getItemCount(): Int {
        return ds.size
    }
}