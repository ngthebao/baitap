package com.example.hoctap3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.hoctap3.model.MonAn
import com.example.hoctap3.model.TheLoai
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddvaEditMonAnActivity : AppCompatActivity() {
    var nhanimg: String? = null
    lateinit var dbRef : DatabaseReference
    lateinit var theLoaiRef : DatabaseReference
    lateinit var edtTenmon : EditText
    lateinit var edtGia : EditText
    lateinit var spnTheLoai : Spinner
    lateinit var imgMonan : ImageView
    lateinit var btnAddMonAn : Button
    lateinit var txtAddEdit : TextView
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addva_edit_mon_an)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtAddEdit = findViewById(R.id.txt_add_edit)
        edtGia = findViewById(R.id.edt_add_gia)
        edtTenmon = findViewById(R.id.edt_add_tenmonan)
        spnTheLoai = findViewById(R.id.spn_theloai)
        imgMonan = findViewById(R.id.img_add_monan)
        btnAddMonAn = findViewById(R.id.btn_add_monan)

        val action = intent.getStringExtra("action")
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                nhanimg = result.data?.getStringExtra("anhmonan")
                if (nhanimg != null) {
                    Glide.with(this).load(nhanimg).into(imgMonan)
                } else {
                    Toast.makeText(this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show()
                }
            }
        }
        imgMonan.setOnClickListener {
            val intent = Intent(this, ImageMonAn::class.java)
            imagePickerLauncher.launch(intent)
        }

        dbRef = FirebaseDatabase.getInstance().getReference("monan")
        theLoaiRef = FirebaseDatabase.getInstance().getReference("theloai_monan")

        val listOfTheLoai = mutableListOf<TheLoai>()
        theLoaiRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfTheLoai.clear()
                if (snapshot.exists()) {
                    for (theLoaiSnapshot in snapshot.children) {
                        val theLoai = theLoaiSnapshot.getValue(TheLoai::class.java)
                        if (theLoai != null) {
                            listOfTheLoai.add(theLoai)
                        }
                    }
                    val adapter = ArrayAdapter(this@AddvaEditMonAnActivity, android.R.layout.simple_spinner_item, listOfTheLoai.map { it.tenTheLoai })
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnTheLoai.adapter = adapter
                    val tenTheLoai = intent.getStringExtra("itemTenTheLoai")
                    val position = listOfTheLoai.indexOfFirst { it.tenTheLoai == tenTheLoai }
                    if (position >= 0) {
                        spnTheLoai.setSelection(position)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        if(action == "add"){
            btnAddMonAn.setOnClickListener {
                val tenMonan = edtTenmon.text.toString()
                val selectedTheLoaiPosition = spnTheLoai.selectedItemPosition
                val selectedTheLoai = listOfTheLoai[selectedTheLoaiPosition]
                val idTheLoai = selectedTheLoai.id
                val tenTheLoai = selectedTheLoai.tenTheLoai
                val gia = edtGia.text.toString()
                val idMonan = dbRef.push().key!!
                Glide.with(this).load(nhanimg).into(imgMonan)
                val monan = MonAn(idMonan,nhanimg!!,tenMonan,idTheLoai,tenTheLoai,gia)
                dbRef.child(idMonan).setValue(monan)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Thêm món ăn thành công", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Thêm món ăn thất bại", Toast.LENGTH_LONG).show()
                    }
            }
        }else {
            btnAddMonAn.setText("Sửa")
            val id = intent.getStringExtra("itemId")
            val tenMonAn = intent.getStringExtra("itemTenMonAn")
            val imgxx = intent.getStringExtra("itemImg")
            val gia = intent.getStringExtra("itemGia")
            val idTheLoai = intent.getStringExtra("idTheLoai")

            Glide.with(this).load(imgxx).into(imgMonan)
            edtTenmon.setText(tenMonAn)
            edtGia.setText(gia)
            txtAddEdit.setText("Sửa món ăn")
            btnAddMonAn.setOnClickListener {
                val idd: String = id.toString()
                val tenNew = edtTenmon.text.toString()
                val giaNew = edtGia.text.toString()
                val selectedTheLoaiPosition = spnTheLoai.selectedItemPosition
                val selectedTheLoai = listOfTheLoai[selectedTheLoaiPosition]
                val idTheLoaiRl = selectedTheLoai.id
                val theLoaiNew = selectedTheLoai.tenTheLoai
                val imgToSave = nhanimg ?: imgxx
                val monan = MonAn(idd,imgToSave!!,tenNew,idTheLoaiRl, theLoaiNew,giaNew)
                dbRef.child(idd).setValue(monan)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_LONG).show()}
                finish()
            }
        }
    }
}
