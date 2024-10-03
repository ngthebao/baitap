package com.example.hoctap3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.adapter.MonAnAdapter
import com.example.hoctap3.interfaceClick.ItemClickListener
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.OrderAdmin
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartDonHangAcitvity : AppCompatActivity() {
    private var userId: String? = null
    lateinit var rvCart : RecyclerView
    lateinit var monAnAdapter: MonAnAdapter
    lateinit var dsMonAn: MutableList<MonAn>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart_don_hang_acitvity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvCart = findViewById(R.id.rv_order_cart_user)
        dsMonAn = mutableListOf()
        monAnAdapter = MonAnAdapter(dsMonAn, object : ItemClickListener {
            override fun theloaiEdit(pos: Int) {
                TODO("Not yet implemented")
            }

            override fun theloaiDelete(pos: Int) {
                TODO("Not yet implemented")
            }

        })
        rvCart.adapter = monAnAdapter
        rvCart.layoutManager = LinearLayoutManager(this)
        userId = intent.getStringExtra("userId")
        if (userId != null) {
            getChiTietDonHang(userId!!)
        }
    }

    private fun getChiTietDonHang(userId: String) {
        val ordersRef = FirebaseDatabase.getInstance().getReference("AdminOrders")
        ordersRef.orderByChild("userId").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dsMonAn.clear()
                    for (orderSnapshot in snapshot.children) {
                        val donHang = orderSnapshot.getValue(OrderAdmin::class.java)
                        if (donHang != null) {
                            dsMonAn.addAll(donHang.cart)
                        }
                    }
                    monAnAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartDonHangAcitvity, "Lỗi khi lấy đơn hàng", Toast.LENGTH_SHORT).show()
                }
            })
    }
}