package com.example.hoctap3.fragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.MainActivity
import com.example.hoctap3.R
import com.example.hoctap3.adapter.CartGuestAdapter
import com.example.hoctap3.model.Cart
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private var userId: String? = null
    lateinit var rvCart : RecyclerView
    lateinit var cartGuestAdapter : CartGuestAdapter
    lateinit var dsMonAn: MutableList<MonAn>
    lateinit var dsCart : MutableList<Cart>
    lateinit var cartDef : DatabaseReference
    lateinit var btnBuy : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        rvCart = view.findViewById(R.id.rv_cart)
        btnBuy = view.findViewById(R.id.btn_buy)
        dsMonAn = mutableListOf<MonAn>()
        cartGuestAdapter = CartGuestAdapter(dsMonAn)
        rvCart.adapter = cartGuestAdapter
        rvCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        val sharedPreferences = requireContext().getSharedPreferences("UserPref", MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null)
        GetThongTinCart()
        SetThongTinDonHang()
        return view
    }

    private fun SetThongTinDonHang() {
        btnBuy.setOnClickListener {
            val userRef = FirebaseDatabase.getInstance().getReference("User/$userId")
            val ordersRef = FirebaseDatabase.getInstance().getReference("AdminOrders")

            // Lấy thông tin người dùng
            userRef.child("tentaikhoan").get().addOnSuccessListener { snapshot ->
                val userName = snapshot.value.toString()
                val orderId = ordersRef.push().key
                val orderData = hashMapOf<String, Any>(
                    "userId" to userId!!,
                    "userName" to userName,
                    "cart" to dsMonAn.map { it.toMap() }
                )
                ordersRef.child(orderId!!).setValue(orderData).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to place order.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun GetThongTinCart() {
        cartDef = FirebaseDatabase.getInstance().getReference("User/$userId/cart")
        cartDef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    dsMonAn.clear()
                    for(cartSnapshot in snapshot.children){
                        val monan  = cartSnapshot.getValue(MonAn::class.java)
                        dsMonAn.add(monan!!)
                    }
                    cartGuestAdapter.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}