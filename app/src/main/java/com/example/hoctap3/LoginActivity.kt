package com.example.hoctap3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hoctap3.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    lateinit var btnDangnhap: Button
    lateinit var rdoQtv: RadioButton
    lateinit var rdoKhach: RadioButton
    lateinit var edtTaikhoan: EditText
    lateinit var edtPass: EditText
    lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnDangnhap = findViewById(R.id.btn_dangnhap)
        rdoQtv = findViewById(R.id.rdo_qtv)
        rdoKhach = findViewById(R.id.rdo_khach)
        edtTaikhoan = findViewById(R.id.edt_tendangnhap)
        edtPass = findViewById(R.id.edt_pass)
        btnDangnhap.setOnClickListener {
            dbRef = FirebaseDatabase.getInstance().getReference("User")
            val tentk = edtTaikhoan.text.toString()
            val pass = edtPass.text.toString()
            if (tentk.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            dbRef.orderByChild("tentaikhoan").equalTo(tentk)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val user = userSnapshot.getValue(User::class.java)
                                if (user != null && user.pass == pass) {
                                    val sharedPreferences =
                                        getSharedPreferences("UserPref", MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putString("userId", user.id)
                                    editor.apply()
                                    if (rdoQtv.isChecked && user.role == "admin") {
                                        val intent =
                                            Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.putExtra("role", "admin")
                                        startActivity(intent)
                                    } else if (rdoKhach.isChecked && user.role == "guest") {
                                        val intent =
                                            Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.putExtra("role", "guest")
                                        intent.putExtra("userId", user.id)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Vui lòng chọn lại phiên đăng nhập",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Mật khẩu không chính xác",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Tên tài khoản không tồn tại",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Lỗi truy vấn dữ liệu: ${error.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
        }
    }
}