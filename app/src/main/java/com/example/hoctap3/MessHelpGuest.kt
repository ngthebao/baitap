package com.example.hoctap3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoctap3.adapter.MessAdapter
import com.example.hoctap3.adapter.MessAdapterGuest
import com.example.hoctap3.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessHelpGuest : AppCompatActivity() {
    var userId: String? = null
    lateinit var rvChat: RecyclerView
    lateinit var etMessage: EditText
    lateinit var btnSend: Button
    lateinit var messageAdapter: MessAdapterGuest
    lateinit var messageList : MutableList<Message>
    lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mess_help_guest)
        val sharedPreferences = this.getSharedPreferences("UserPref", MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null)
        rvChat = findViewById(R.id.rv_chat)
        etMessage = findViewById(R.id.edt_chat)
        btnSend = findViewById(R.id.btn_chat)
        messageList = mutableListOf<Message>()
        messageAdapter = MessAdapterGuest(messageList,userId!!)
        rvChat.adapter = messageAdapter
        rvChat.layoutManager = LinearLayoutManager(this)
        dbRef = FirebaseDatabase.getInstance().getReference("chats").child("chatRoomId")

        loadMessages()
        btnSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        if (userId != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId!!)
            userRef.child("userName").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userName = dataSnapshot.getValue(String::class.java) ?: "User"  // Cung cấp giá trị mặc định

                    // Lấy message từ EditText
                    val messageText = etMessage.text.toString()
                    if (messageText.isNotEmpty()) {
                        val messageId = dbRef.push().key
                        val message = Message(
                            userId = userId,
                            userName = userName,
                            messageId = messageId,
                            message = messageText,
                            timestamp = System.currentTimeMillis()
                        )

                        messageId?.let {
                            dbRef.child(it).setValue(message)
                            etMessage.text.clear()  // Xóa text sau khi gửi
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi nếu cần
                    Toast.makeText(this@MessHelpGuest, "Lỗi khi lấy tên người dùng: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun loadMessages() {
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let { messageList.add(it) }
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}