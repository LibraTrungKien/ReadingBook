package com.example.reading.data.mapper

import com.example.reading.data.dto.AccountDTO
import com.example.reading.domain.model.Account

fun AccountDTO.toModel(): Account {
    val model = this
    return Account(
        id = model.id,
        username = model.username,
        email = model.email,
        password = model.password,
        avatar = model.avatar,
        permission = model.permission
    )
}

fun Account.toDTO(): AccountDTO {
    val model = this
    return AccountDTO(
        id = model.id,
        username = model.username,
        email = model.email,
        password = model.password,
        avatar = model.avatar,
        permission = model.permission,
    )
}