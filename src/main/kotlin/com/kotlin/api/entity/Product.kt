package com.kotlin.api.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @field:NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    var name: String,

    @Column(length = 500)
    var description: String? = null,

    @field:Positive(message = "Preço deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal,

    @field:Positive(message = "Estoque deve ser positivo")
    @Column(nullable = false)
    var stock: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var category: Category,

    @Column(nullable = false)
    var active: Boolean = true,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    // Construtor sem parâmetros para JPA
    constructor() : this(
        id = 0,
        name = "",
        description = null,
        price = BigDecimal.ZERO,
        stock = 0,
        category = Category.OTHER,
        active = true,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Product(id=$id, name='$name', price=$price, stock=$stock, category=$category, active=$active)"
    }
}

enum class Category {
    ELECTRONICS, CLOTHING, FOOD, BOOKS, HOME, SPORTS, OTHER
}