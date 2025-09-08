package com.kotlin.api.dto

import com.kotlin.api.entity.Category
import java.math.BigDecimal

data class ProductUpdateDto(
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val stock: Int?,
    val category: Category?,
    val active: Boolean?
)