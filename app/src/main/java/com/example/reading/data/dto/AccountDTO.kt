package com.example.reading.data.dto

data class AccountDTO(
    private val id: Int,
    private val username: String,
    private val email: String,
    private val password: String,
    private val avatar: String,
    private val gender: Boolean,
    private val phone: String
)