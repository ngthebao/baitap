package com.example.hoctap3.model

class User {
    var id: String = ""
    var tentaikhoan: String =""
    var pass: String=""
    var role: String=""

    constructor()
    constructor(id: String, tentaikhoan: String, pass: String, role: String) {
        this.id = id
        this.tentaikhoan = tentaikhoan
        this.pass = pass
        this.role = role
    }
}
