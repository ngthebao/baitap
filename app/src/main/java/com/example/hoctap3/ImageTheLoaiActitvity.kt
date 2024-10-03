package com.example.hoctap3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.adapter.ImageTheLoaiAdapter
import com.google.firebase.storage.FirebaseStorage


class ImageTheLoaiActitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_the_loai_actitvity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val rvImage = findViewById<RecyclerView>(R.id.rv_image)
        val imageUrls = mutableListOf<String>()

        val imageAdapter = ImageTheLoaiAdapter(this, imageUrls, object : ImageTheLoaiAdapter.OnClickItemListener {
            override fun onClickItem(position: Int) {
                val selectedImageUrl = imageUrls[position] // URL của ảnh được chọn
                val resultIntent = Intent()
                resultIntent.putExtra("anh", selectedImageUrl)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })
        rvImage.adapter = imageAdapter
        rvImage.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        rvImage.setHasFixedSize(true)

        val storageRef = FirebaseStorage.getInstance().getReference("images")
        storageRef.listAll().addOnSuccessListener { result ->
            val tempImageUrls = mutableListOf<String>()
            for (fileRef in result.items) {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    tempImageUrls.add(uri.toString())
                    if (tempImageUrls.size == result.items.size) {
                        imageUrls.addAll(tempImageUrls)
                        imageAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }
}