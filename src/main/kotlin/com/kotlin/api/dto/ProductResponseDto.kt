package com.kotlin.api.dto

import com.kotlin.api.entity.Category
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductResponseDto(
    val id: Long,
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val stock: Int,
    val category: Category,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)