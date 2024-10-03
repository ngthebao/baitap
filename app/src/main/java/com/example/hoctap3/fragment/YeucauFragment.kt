package com.example.hoctap3.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.CartDonHangAcitvity
import com.example.hoctap3.R
import com.example.hoctap3.adapter.CartGuestAdapter
import com.example.hoctap3.adapter.DonHangGuestAdapter
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.OrderAdmin
import com.example.hoctap3.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class YeucauFragment : Fragment() {
    lateinit var donHangGuestAdapter: DonHangGuestAdapter
    lateinit var dsDonHang: MutableList<OrderAdmin>
    lateinit var rvDonHang : RecyclerView
    lateinit var rvItemCart : RecyclerView
    lateinit var donHangRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_yeu_cau, container, false)

        rvDonHang = view.findViewById(R.id.rv_donhang)
        dsDonHang = mutableListOf<OrderAdmin>()
        donHangGuestAdapter = DonHangGuestAdapter(dsDonHang, object : DonHangGuestAdapter.OnClickItemListener{
            override fun onClickItem(position: Int) {
                val intent = Intent(requireContext(), CartDonHangAcitvity::class.java)
                intent.putExtra("userId", dsDonHang[position].userId)
                startActivity(intent)
            }

        })
        rvDonHang.adapter = donHangGuestAdapter
        rvDonHang.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        GetThongTinCart()

        return view
    }

    private fun GetThongTinCart() {
        donHangRef = FirebaseDatabase.getInstance().getReference("AdminOrders")
        donHangRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dsDonHang.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(OrderAdmin::class.java)
                    dsDonHang.add(order!!)
                }
                donHangGuestAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load orders.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}