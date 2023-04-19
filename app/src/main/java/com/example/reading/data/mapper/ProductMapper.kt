package com.example.reading.data.mapper

import com.example.reading.data.dto.ProductsDTO
import com.example.reading.domain.model.Products

fun ProductsDTO.toModel(): Products {
    val dto = this
    return Products(
        id = dto.id,
        userId = dto.userId,
        stories = dto.stories
    )
}

fun Products.toDTO(): ProductsDTO {
    val model = this
    return ProductsDTO(
        id = model.id,
        userId = model.userId,
        stories = model.stories
    )
}