package com.example.hoctap3.model

data class OrderAdmin(
    val userId: String = "",
    val userName: String = "",
    val cart: List<MonAn> = listOf()
)