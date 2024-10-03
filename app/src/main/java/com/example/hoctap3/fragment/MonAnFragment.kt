package com.example.hoctap3.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.AddvaEditMonAnActivity
import com.example.hoctap3.interfaceClick.ItemClickListener
import com.example.hoctap3.R
import com.example.hoctap3.adapter.MonAnAdapter
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.TheLoai
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MonAnFragment : Fragment() {
    lateinit var searchMonAn : SearchView
    lateinit var monAnAdapter: MonAnAdapter
    lateinit var ds : MutableList<MonAn>
    lateinit var rvMonan : RecyclerView
    lateinit var dbRef : DatabaseReference
    lateinit var btnAddMonan : FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mon_an, container, false)
        searchMonAn = view.findViewById(R.id.sv_search_monan)
        rvMonan = view.findViewById(R.id.rv_monan)
        btnAddMonan = view.findViewById(R.id.btn_add_monan)
        ds = mutableListOf<MonAn>()
        dbRef = FirebaseDatabase.getInstance().getReference("monan")
        searchMonAn.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                getMonAn(p0)
                return true
            }

        })
        monAnAdapter = MonAnAdapter(ds, object : ItemClickListener {
            override fun theloaiEdit(pos: Int) {
                val intent = Intent(requireContext(), AddvaEditMonAnActivity::class.java)
                intent.putExtra("action", "edit")
                intent.putExtra("itemId", ds[pos].id)
                intent.putExtra("itemTenMonAn", ds[pos].tenMonan)
                intent.putExtra("itemImg",ds[pos].imageMonan)
                intent.putExtra("itemTenTheLoai", ds[pos].tenTheLoai)
                intent.putExtra("itemGia",ds[pos].gia)
                intent.putExtra("idTheLoai",ds[pos].idTheLoai)
                startActivity(intent)
            }

            override fun theloaiDelete(pos: Int) {
                TODO("Not yet implemented")
            }

        })

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists()){
                    for(monanSnapshot in snapshot.children){
                        val monan = monanSnapshot.getValue(MonAn::class.java)
                        val idTheloai = monan?.idTheLoai
                        val theloaiRef = FirebaseDatabase.getInstance().getReference("theloai_monan").child(idTheloai!!)
                        theloaiRef.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val tenTheLoai = snapshot.child("tenTheLoai").value.toString()
                                monan.tenTheLoai = tenTheLoai
                                ds.add(monan)
                                monAnAdapter.notifyDataSetChanged()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        rvMonan.adapter = monAnAdapter
        rvMonan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        btnAddMonan.setOnClickListener {
            val intent = Intent(requireContext(), AddvaEditMonAnActivity::class.java)
            intent.putExtra("action", "add")
            startActivity(intent)
        }
        return view
    }

    private fun getMonAn(p0: String?) {
        val filteredList = mutableListOf<MonAn>()

        if (!p0.isNullOrEmpty()) {
            val getAll = FirebaseDatabase.getInstance().getReference("monan")
            getAll.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    filteredList.clear()
                    for (snapshot in dataSnapshot.children) {
                        val monAn = snapshot.getValue(MonAn::class.java)
                        if (monAn != null && monAn.tenMonan.contains(p0, ignoreCase = true)) {
                            filteredList.add(monAn)
                        }
                    }
                    monAnAdapter.updateData(filteredList)
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
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists()){
                    for(monanSnapshot in snapshot.children){
                        val monan = monanSnapshot.getValue(MonAn::class.java)
                        val idTheloai = monan?.idTheLoai
                        val theloaiRef = FirebaseDatabase.getInstance().getReference("theloai_monan").child(idTheloai!!)
                        theloaiRef.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val tenTheLoai = snapshot.child("tenTheLoai").value.toString()
                                monan.tenTheLoai = tenTheLoai
                                ds.add(monan)
                                monAnAdapter.notifyDataSetChanged()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}