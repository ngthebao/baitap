package com.example.hoctap3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.hoctap3.adapter.CartGuestAdapter
import com.example.hoctap3.adapter.ViewAdminAdapter
import com.example.hoctap3.adapter.ViewGuestAdapter
import com.example.hoctap3.fragment.CartFragment
import com.example.hoctap3.fragment.TheLoaiFragment
import com.example.hoctap3.model.MonAn
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var countItem : Int = 0
    lateinit var viewAdmin : ViewPager2
    lateinit var bnvAdmin : BottomNavigationView
    lateinit var viewGuest : ViewPager2
    lateinit var bnvGuest : BottomNavigationView
    lateinit var viewAdminAdapter: ViewAdminAdapter
    lateinit var viewGuestAdapter: ViewGuestAdapter
    lateinit var badge : BadgeDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userRole = intent.getStringExtra("role")
        if (userRole == "admin") {
            setContentView(R.layout.activity_admin)
            viewAdmin = findViewById(R.id.vp2_admin)
            bnvAdmin = findViewById(R.id.bnv_admin)
            viewAdminAdapter = ViewAdminAdapter(supportFragmentManager, lifecycle)
            viewAdmin.adapter = viewAdminAdapter

            viewAdmin.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bnvAdmin.menu.getItem(position).isChecked = true
                }
            })

            bnvAdmin.setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.theloai -> viewAdmin.currentItem = 0
                    R.id.monan -> viewAdmin.currentItem = 1
                    R.id.yeucau -> viewAdmin.currentItem = 2
                    R.id.phanhoi -> viewAdmin.currentItem = 3
                    R.id.taikhoan -> viewAdmin.currentItem = 4
                }
                true
            }
        } else if (userRole == "guest") {
            setContentView(R.layout.activity_guest)
            viewGuest = findViewById(R.id.vp2_guest)
            bnvGuest = findViewById(R.id.bnv_guest)
            viewGuestAdapter = ViewGuestAdapter(supportFragmentManager, lifecycle)
            viewGuest.adapter = viewGuestAdapter

            viewGuest.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bnvGuest.menu.getItem(position).isChecked = true
                }
            })

            bnvGuest.setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.home -> viewGuest.currentItem = 0
                    R.id.cart -> viewGuest.currentItem = 1
                    R.id.favorite -> viewGuest.currentItem = 2
                    R.id.taikhoan -> viewGuest.currentItem = 3
                }
                true
            }
        } else {
            Toast.makeText(this, "Error: User role undefined!", Toast.LENGTH_SHORT).show()
        }
    }
}