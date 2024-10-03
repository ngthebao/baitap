package com.example.hoctap3.model

class MonAn {
    var id : String? = ""
    var imageMonan : String = ""
    var tenMonan : String =""
    var idTheLoai : String? =""
    var tenTheLoai : String? =""
    var gia : String =""
    constructor()
    constructor(
        id: String?,
        imageMonan: String,
        tenMonan: String,
        idTheLoai: String?,
        tenTheLoai: String?,
        gia: String
    ) {
        this.id = id
        this.imageMonan = imageMonan
        this.tenMonan = tenMonan
        this.idTheLoai = idTheLoai
        this.tenTheLoai = tenTheLoai
        this.gia = gia
    }
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["id"] = id ?: ""
        result["imageMonan"] = imageMonan
        result["tenMonan"] = tenMonan
        result["idTheLoai"] = idTheLoai ?: ""
        result["tenTheLoai"] = tenTheLoai ?: ""
        result["gia"] = gia
        return result
    }
}