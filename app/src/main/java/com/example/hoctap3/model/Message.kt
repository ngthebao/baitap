package com.example.hoctap3.model

class Message {
    var userId : String? = null
    var userName : String =""
    var messageId: String? = null
    var message: String = ""
    var timestamp: Long = 0L
    constructor()
    constructor(
        userId: String?,
        userName: String,
        messageId: String?,
        message: String,
        timestamp: Long
    ) {
        this.userId = userId
        this.userName = userName
        this.messageId = messageId
        this.message = message
        this.timestamp = timestamp
    }
}