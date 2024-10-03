package com.example.hoctap3

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoiPass : AppCompatActivity() {
    lateinit var edtPassOld : EditText
    lateinit var edtPassNew : EditText
    lateinit var btnSave : Button
    lateinit var dbRef: DatabaseReference
    lateinit var currentUserId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doi_pass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        edtPassOld = findViewById(R.id.edt_pass_old)
        edtPassNew = findViewById(R.id.edt_pass_new)
        btnSave = findViewById(R.id.btn_save)
        dbRef = FirebaseDatabase.getInstance().getReference("User")
        val sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        currentUserId = sharedPreferences.getString("userId", "") ?: ""
        btnSave.setOnClickListener {
            val oldPassword = edtPassOld.text.toString().trim()
            val newPassword = edtPassNew.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mật khẩu cũ và mới", Toast.LENGTH_SHORT).show()
            } else {
                verifyOldPassword(oldPassword, newPassword)
            }
        }

    }

    private fun verifyOldPassword(oldPassword: String, newPassword: String) {
        dbRef.child(currentUserId).child("pass").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentPassword = dataSnapshot.getValue(String::class.java)
                Log.d("DoiPass", "Current Password: $currentPassword, Entered Old Password: $oldPassword")
                if (currentPassword == oldPassword) {
                    changePassword(newPassword)
                } else {
                    Toast.makeText(this@DoiPass, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DoiPass, "Đã xảy ra lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changePassword(newPassword: String) {
        dbRef.child(currentUserId).child("pass").setValue(newPassword)
            .addOnSuccessListener {
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Đã xảy ra lỗi: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}