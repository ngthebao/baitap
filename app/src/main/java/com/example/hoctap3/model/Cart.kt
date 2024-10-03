package com.example.hoctap3.model

class Cart {
    var id : String = ""
    var userId : String = ""
    var monAn : MutableList<MonAn> = mutableListOf()
    var soLuong : Int = 0
    var totalPrice: Double = 0.0
    constructor()
    constructor( id : String,userId: String, monAn: MutableList<MonAn>, soLuong : Int,totalPrice: Double) {
        this.id = id
        this.userId = userId
        this.monAn = monAn
        this.soLuong = soLuong
        this.totalPrice = totalPrice
    }
}