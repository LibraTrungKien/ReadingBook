package com.example.reading.data.mapper

import com.example.reading.data.dto.LoginDTO
import com.example.reading.domain.model.Login

fun Login.toDTO(): LoginDTO {
    val model = this
    return LoginDTO(
        email = model.email,
        password = model.password
    )
}