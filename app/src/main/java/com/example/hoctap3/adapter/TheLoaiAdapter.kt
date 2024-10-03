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
import com.example.hoctap3.model.TheLoai

class TheLoaiAdapter(var ds : MutableList<TheLoai>, val listener : ItemClickListener) : RecyclerView.Adapter<TheLoaiAdapter.TheLoaiViewHolder>(){
    private var fullList: List<TheLoai> = ArrayList(ds)
    init {
        fullList = ArrayList(ds)
    }
    class TheLoaiViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tenTheLoai = itemView.findViewById<TextView>(R.id.txt_ten_theloai)
        val imgTheLoai = itemView.findViewById<ImageView>(R.id.img_theloai)
        val imgBtnEdit = itemView.findViewById<ImageButton>(R.id.imgbtn_edit_monan)
        val imgBtnDelete = itemView.findViewById<ImageButton>(R.id.imgbnt_delete_monan)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheLoaiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_theloai,parent,false)
        return TheLoaiViewHolder(view)
    }

    override fun onBindViewHolder(holder: TheLoaiViewHolder, position: Int) {
        holder.itemView.apply {
            holder.tenTheLoai.text = ds[position].tenTheLoai
            val imgURL = ds[position]
            Glide.with(holder.itemView.context).load(imgURL.imgTheLoai).into(holder.imgTheLoai)
            holder.imgBtnEdit.setOnClickListener{
                listener.theloaiEdit(position)
            }
            holder.imgBtnDelete.setOnClickListener {
                listener.theloaiDelete(position)
            }

        }

    }

    override fun getItemCount(): Int {
        return ds.size
    }
    fun updateData(newList: List<TheLoai>) {
        ds.clear() // Xóa danh sách cũ
        ds.addAll(newList) // Thêm danh sách mới
        notifyDataSetChanged() // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }
}