package com.example.hoctap3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hoctap3.interfaceClick.ItemClickListener
import com.example.hoctap3.R
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.TheLoai

class MonAnAdapter(var ds : MutableList<MonAn>, private val listener: ItemClickListener) : RecyclerView.Adapter<MonAnAdapter.MonAnViewHolder>(){
    inner class MonAnViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tenMonAn = itemView.findViewById<TextView>(R.id.txt_ten_monan)
        var tenTheLoai = itemView.findViewById<TextView>(R.id.txt_ten_theloai)
        var giaMonAn = itemView.findViewById<TextView>(R.id.txt_gia_monan)
        var imgMonan = itemView.findViewById<ImageView>(R.id.img_monan)
        val btnEdit = itemView.findViewById<ImageButton>(R.id.imgbtn_edit_monan)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.imgbnt_delete_monan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonAnViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_monan, parent, false)
        return MonAnViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonAnViewHolder, position: Int) {
        holder.itemView.apply {
            val monan = ds[position]
            holder.tenMonAn.text = monan.tenMonan
            holder.tenTheLoai.text = monan.tenTheLoai
            holder.giaMonAn.text = monan.gia
            Glide.with(holder.itemView.context).load(monan.imageMonan).into(holder.imgMonan)
            holder.btnEdit.setOnClickListener {
                listener.theloaiEdit(position)
            }
            holder.btnDelete.setOnClickListener {
                listener.theloaiDelete(position)
            }
        }
    }

    override fun getItemCount(): Int {
       return ds.size
    }
    fun updateData(newList: List<MonAn>) {
        ds.clear() // Xóa danh sách cũ
        ds.addAll(newList) // Thêm danh sách mới
        notifyDataSetChanged() // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }
}