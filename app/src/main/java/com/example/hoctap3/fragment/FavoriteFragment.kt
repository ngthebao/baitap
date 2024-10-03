package com.example.hoctap3.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.R
import com.example.hoctap3.adapter.CartGuestAdapter
import com.example.hoctap3.model.Cart
import com.example.hoctap3.model.MonAn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavoriteFragment : Fragment() {
    private var userId: String? = null
    lateinit var rvFavorite : RecyclerView
    lateinit var cartGuestAdapter : CartGuestAdapter
    lateinit var dsMonAn: MutableList<MonAn>
    lateinit var favoriteDef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        rvFavorite = view.findViewById(R.id.rv_favorite)
        dsMonAn = mutableListOf<MonAn>()
        cartGuestAdapter = CartGuestAdapter(dsMonAn)
        rvFavorite.adapter = cartGuestAdapter
        rvFavorite.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        val sharedPreferences = requireContext().getSharedPreferences("UserPref", MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null)
        GetThongTinCart()
        return view
    }

    private fun GetThongTinCart() {
        favoriteDef = FirebaseDatabase.getInstance().getReference("User/$userId/favorite")
        favoriteDef.addValueEventListener(object : ValueEventListener {
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