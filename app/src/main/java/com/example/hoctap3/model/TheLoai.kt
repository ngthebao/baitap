package com.example.hoctap3.model

class TheLoai {
    var id : String? =""
    var tenTheLoai : String =""
    var imgTheLoai : String =""
    constructor()
    constructor(id: String?, imgTheLoai: String, tenTheLoai: String) {
        this.id = id
        this.imgTheLoai = imgTheLoai
        this.tenTheLoai = tenTheLoai
    }
}