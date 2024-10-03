package com.example.hoctap3.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.AddItemTheLoaiActivity
import com.example.hoctap3.interfaceClick.ItemClickListener
import com.example.hoctap3.R
import com.example.hoctap3.model.TheLoai
import com.example.hoctap3.adapter.TheLoaiAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TheLoaiFragment : Fragment() {
    lateinit var theLoaiAdapter: TheLoaiAdapter
    lateinit var ds : MutableList<TheLoai>
    lateinit var rvTheLoai : RecyclerView
    lateinit var searchTheLoai : SearchView
    lateinit var dbRef : DatabaseReference
    lateinit var btnAdd : FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_the_loai, container, false)
        rvTheLoai = view.findViewById(R.id.rv_theloai)
        searchTheLoai = view.findViewById(R.id.sv_search_theloai)
        btnAdd = view.findViewById(R.id.btn_add)
        ds = mutableListOf<TheLoai>()
        dbRef = FirebaseDatabase.getInstance().getReference("theloai_monan")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists()){
                    for (theloaiSnapshot in snapshot.children){
                        val theloaiData = theloaiSnapshot.getValue(TheLoai::class.java)
                        ds.add(theloaiData!!)
                    }
                }
                theLoaiAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        theLoaiAdapter = TheLoaiAdapter(ds, object : ItemClickListener {
            override fun theloaiEdit(pos: Int) {
                val intent = Intent(requireContext(), AddItemTheLoaiActivity::class.java)
                intent.putExtra("action", "edit")
                intent.putExtra("itemId", ds[pos].id)
                intent.putExtra("itemTen", ds[pos].tenTheLoai)
                intent.putExtra("itemImg",ds[pos].imgTheLoai)
                startActivity(intent)
            }

            override fun theloaiDelete(pos: Int) {
                dbRef = FirebaseDatabase.getInstance().getReference("theloai_monan").child(ds[pos].id.toString())
                dbRef.removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(),"Xóa thành công", Toast.LENGTH_LONG).show()
                    }
            }

        })
        rvTheLoai.adapter = theLoaiAdapter
        rvTheLoai.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddItemTheLoaiActivity::class.java)
            intent.putExtra("action", "add")
            startActivity(intent)
        }
        searchTheLoai.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                getTheLoai(p0)
                return true
            }

        })

        return view
    }

    private fun getTheLoai(p0: String?) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("theloai_monan")
        val filteredList = mutableListOf<TheLoai>()

        if (!p0.isNullOrEmpty()) {
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    filteredList.clear()
                    for (snapshot in dataSnapshot.children) {
                        val theLoai = snapshot.getValue(TheLoai::class.java)
                        if (theLoai != null && theLoai.tenTheLoai.contains(p0, ignoreCase = true)) {
                            filteredList.add(theLoai)
                        }
                    }
                    theLoaiAdapter.updateData(filteredList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Xử lý lỗi
                    Log.e("FirebaseError", databaseError.message)
                }
            })
        } else {
            getAllTheLoaiFromFirebase()
        }
    }

    private fun getAllTheLoaiFromFirebase() {
        dbRef = FirebaseDatabase.getInstance().getReference("theloai_monan")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists()){
                    for (theloaiSnapshot in snapshot.children){
                        val theloaiData = theloaiSnapshot.getValue(TheLoai::class.java)
                        ds.add(theloaiData!!)
                    }
                }
                theLoaiAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}