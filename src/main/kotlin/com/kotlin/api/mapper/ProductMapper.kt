package com.kotlin.api.mapper

import com.kotlin.api.dto.ProductRequestDto
import com.kotlin.api.dto.ProductResponseDto
import com.kotlin.api.entity.Product
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ProductMapper {

    fun toEntity(dto: ProductRequestDto): Product {
        return Product(
            name = dto.name,
            description = dto.description,
            price = dto.price,
            stock = dto.stock,
            category = dto.category,
            active = dto.active,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toDto(entity: Product): ProductResponseDto {
        return ProductResponseDto(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            price = entity.price,
            stock = entity.stock,
            category = entity.category,
            active = entity.active,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}