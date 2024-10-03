package com.example.hoctap3.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.MessHelpGuest
import com.example.hoctap3.R
import com.example.hoctap3.adapter.MessAdapter
import com.example.hoctap3.model.Message
import com.example.hoctap3.model.OrderAdmin
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PhanhoiFragment : Fragment() {
    private var userId: String? = null
    lateinit var rv_user : RecyclerView
    lateinit var messAdapter: MessAdapter
    lateinit var messList : MutableList<Message>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phan_hoi, container, false)
        rv_user = view.findViewById(R.id.rv_user_phanhoi)
        messList = mutableListOf<Message>()
        messAdapter = MessAdapter(messList, object :MessAdapter.OnClickItemListener{
            override fun onClickItem(position: Int) {
                val intent = Intent(requireContext(), MessHelpGuest::class.java)
                startActivity(intent)
            }

        })
        rv_user.adapter = messAdapter
        rv_user.layoutManager = LinearLayoutManager(requireContext())
        val sharedPreferences = requireContext().getSharedPreferences("UserPref", MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null)
        if (userId != null) {
            GetThongTinPhanHoi(userId!!)
        }

        return view
    }

    private fun GetThongTinPhanHoi(userId: String) {
        val phanHoiRef = FirebaseDatabase.getInstance().getReference("chats")
        phanHoiRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messList.clear()
                for (messSnapshot in snapshot.children) {
                    val mess = messSnapshot.getValue(Message::class.java)
                    messList.add(mess!!)
                }
                messAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Mess Failer.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}