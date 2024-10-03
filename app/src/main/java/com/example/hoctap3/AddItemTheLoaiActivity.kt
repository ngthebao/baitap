package com.example.hoctap3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.hoctap3.model.TheLoai
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddItemTheLoaiActivity : AppCompatActivity() {
    var nhanimg: String? = null
    lateinit var dbRef : DatabaseReference
    lateinit var edtTheloai : EditText
    lateinit var imgTheloai : ImageView
    lateinit var btnAddTheLoai : Button
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_item_the_loai)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtAddEdit = findViewById<TextView>(R.id.txt_add_edit)
        edtTheloai = findViewById(R.id.edt_add_theloai)
        imgTheloai = findViewById(R.id.img_add_theloai)
        btnAddTheLoai = findViewById(R.id.btn_add_theloai)

        val action = intent.getStringExtra("action")

        dbRef = FirebaseDatabase.getInstance().getReference("theloai_monan")

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                nhanimg = result.data?.getStringExtra("anh")
                if (nhanimg != null) {
                    Glide.with(this).load(nhanimg).into(imgTheloai)
                } else {
                    Toast.makeText(this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show()
                }
            }
        }
        imgTheloai.setOnClickListener {
            val intent = Intent(this, ImageTheLoaiActitvity::class.java)
            imagePickerLauncher.launch(intent)
        }
        if(action =="add"){
            btnAddTheLoai.setOnClickListener {
                val tenTheLoai = edtTheloai.text.toString()
                val idTheloai = dbRef.push().key!!
                Glide.with(this).load(nhanimg).into(imgTheloai)
                val theloai = TheLoai(idTheloai, nhanimg!!, tenTheLoai)
                dbRef.child(idTheloai).setValue(theloai)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_LONG).show()}
            }
        }
        else{
            btnAddTheLoai.setText("Sửa")
            val id = intent.getStringExtra("itemId")
            val ten = intent.getStringExtra("itemTen")
            val imgxx = intent.getStringExtra("itemImg")

            Glide.with(this).load(imgxx).into(imgTheloai)
            edtTheloai.setText(ten)
            txtAddEdit.setText("Sửa thể loại món ăn")
            btnAddTheLoai.setOnClickListener {
                val idd : String = id.toString()
                val tenNew =  edtTheloai.text.toString()
                val imgToSave = nhanimg ?: imgxx
                val theloai = TheLoai(idd, imgToSave!!, tenNew)
                dbRef.child(idd).setValue(theloai)
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