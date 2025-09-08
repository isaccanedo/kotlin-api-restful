package com.kotlin.api.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    val name: String,

    @Column(length = 500)
    val description: String? = null,

    @field:Positive(message = "Preço deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,

    @field:Positive(message = "Estoque deve ser positivo")
    @Column(nullable = false)
    val stock: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val category: Category,

    @Column(nullable = false)
    val active: Boolean = true,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class Category {
    ELECTRONICS, CLOTHING, FOOD, BOOKS, HOME, SPORTS, OTHER
}