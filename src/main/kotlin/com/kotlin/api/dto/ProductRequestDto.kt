package com.kotlin.api.dto

import com.kotlin.api.entity.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProductRequestDto(
    @field:NotBlank(message = "Nome é obrigatório")
    val name: String,

    val description: String?,

    @field:Positive(message = "Preço deve ser positivo")
    val price: BigDecimal,

    @field:Positive(message = "Estoque deve ser positivo")
    val stock: Int,

    val category: Category,
    val active: Boolean = true
)