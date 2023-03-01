package com.example.reading.domain.model

data class Account(
    var id: Int = 0,
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var avatar: String = "",
    var permission: String = "",
    var gender: String = "",
    var phone: String = ""
)