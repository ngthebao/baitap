package com.example.hoctap3.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hoctap3.R
import com.google.firebase.storage.FirebaseStorage
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.example.hoctap3.interfaceClick.ItemClickMonAnGuest
import com.example.hoctap3.adapter.MonAnGuestAdapter
import com.example.hoctap3.adapter.TheLoaiGuestAdapter
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.TheLoai
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    lateinit var theLoaiRef : DatabaseReference
    lateinit var imgSlide: ImageSlider
    lateinit var monAnRef : DatabaseReference
    val imageUrls = mutableListOf<String>()
    val ds = mutableListOf<TheLoai>()
    val dsMonan = mutableListOf<MonAn>()
    lateinit var theLoaiGuestAdapter: TheLoaiGuestAdapter
    lateinit var monAnGuestAdapter: MonAnGuestAdapter
    lateinit var rvTheLoai : RecyclerView
    lateinit var rvMonAn : RecyclerView
    lateinit var textAllTheloai : TextView
    lateinit var textAllMonAn : TextView
    lateinit var userRef : DatabaseReference
    var userId : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvTheLoai = view.findViewById(R.id.rv_theloai_home)
        rvMonAn = view.findViewById(R.id.rv_monan_home)
        imgSlide = view.findViewById(R.id.image_slider_home)
        textAllTheloai = view.findViewById(R.id.txt_all_theloai)
        textAllMonAn = view.findViewById(R.id.txt_all_monan)
        if (imageUrls.isEmpty()) {
            fetchImageUrls()
        }
        userRef = FirebaseDatabase.getInstance().getReference("User")
        val sharedPreferences = requireContext().getSharedPreferences("UserPref", MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null)

        GetThongTinTheLoai()

        GetThongTinMonAn()
        textAllTheloai.setOnClickListener {
            setViewAllTheLoai()
        }
        textAllMonAn.setOnClickListener {
            setViewAllMonAn()
        }
        rvTheLoai.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        rvMonAn.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        return view
    }

    private fun setViewAllMonAn() {
        val viewAllMonAn = layoutInflater.inflate(R.layout.get_all_monan, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(viewAllMonAn)
        val rvAllTheLoai = viewAllMonAn.findViewById<RecyclerView>(R.id.rv_all_monan)
        val adapterAllMonAn = MonAnGuestAdapter(dsMonan, object : ItemClickMonAnGuest{
            override fun monanCart(pos: Int) {
                TODO("Not yet implemented")
            }

            override fun monanFavorite(pos: Int) {
                TODO("Not yet implemented")
            }

        })
        rvAllTheLoai.adapter = adapterAllMonAn
        rvAllTheLoai.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val dialog = builder.create()
        dialog.show()
    }

    private fun setViewAllTheLoai() {
        val dialogView = layoutInflater.inflate(R.layout.get_all_theloai, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val rvAllTheLoai = dialogView.findViewById<RecyclerView>(R.id.rv_all_theloai)
        val alladapter = TheLoaiGuestAdapter(ds)
        rvAllTheLoai.adapter = alladapter
        rvAllTheLoai.layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.HORIZONTAL, false)
        val dialog = builder.create()
        dialog.show()
    }

    private fun GetThongTinMonAn() {
        monAnRef = FirebaseDatabase.getInstance().getReference("monan")
        monAnRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    dsMonan.clear()
                    for (monanSnapshot in snapshot.children){
                        val monan = monanSnapshot.getValue(MonAn::class.java)
                        dsMonan.add(monan!!)
                    }
                    monAnGuestAdapter = MonAnGuestAdapter(dsMonan, object : ItemClickMonAnGuest {
                        override fun monanCart(pos: Int) {
                            if (userId != null) {
                                val carRef = userRef.child(userId!!).child("cart")
                                carRef.child(dsMonan[pos].id!!).setValue(dsMonan[pos])
                                Toast.makeText(requireContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "User không hợp lệ. Hãy đăng nhập.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun monanFavorite(pos: Int) {
                            if (userId != null) {
                                val carRef = userRef.child(userId!!).child("favorite")
                                carRef.child(dsMonan[pos].id!!).setValue(dsMonan[pos])
                                Toast.makeText(requireContext(), "Thêm mục yêu thích thành công", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "User không hợp lệ. Hãy đăng nhập.", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
                    rvMonAn.adapter = monAnGuestAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun GetThongTinTheLoai() {

        theLoaiRef = FirebaseDatabase.getInstance().getReference("theloai_monan")
        theLoaiRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    ds.clear()
                    for (theLoaiSnapshot in snapshot.children){
                        val theloai = theLoaiSnapshot.getValue(TheLoai::class.java)
                        ds.add(theloai!!)
                    }
                    theLoaiGuestAdapter = TheLoaiGuestAdapter(ds)
                    rvTheLoai.adapter = theLoaiGuestAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fetchImageUrls() {
        val storageReference = FirebaseStorage.getInstance().reference.child("imageslideshow/")

        storageReference.listAll().addOnSuccessListener { listResult ->
            for (item in listResult.items) {
                item.downloadUrl.addOnSuccessListener { uri ->
                    imageUrls.add(uri.toString())
                    if (imageUrls.size == listResult.items.size) {
                        setupImageSlider(imageUrls)
                    }
                }
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Không tải được hình ảnh", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupImageSlider(imageUrls: List<String>) {
        val imageList = mutableListOf<SlideModel>()
        for (url in imageUrls) {
            imageList.add(SlideModel(url, ScaleTypes.FIT))
        }
        imgSlide.setImageList(imageList, ScaleTypes.FIT)
    }
}