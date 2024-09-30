package com.example.reading.data.dto

data class AccountDTO(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val avatar: String,
    var permission: Boolean,
)