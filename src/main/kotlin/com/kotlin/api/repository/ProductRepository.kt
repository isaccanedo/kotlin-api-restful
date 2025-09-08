package com.kotlin.api.repository

import com.kotlin.api.entity.Category
import com.kotlin.api.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    fun findByActiveTrue(pageable: Pageable): Page<Product>

    fun findByActiveFalse(pageable: Pageable): Page<Product>

    fun findByCategory(category: Category, pageable: Pageable): Page<Product>

    fun findByCategoryAndActiveTrue(category: Category, pageable: Pageable): Page<Product>

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.active = true")
    fun findByNameContainingIgnoreCaseAndActiveTrue(
        @Param("name") name: String,
        pageable: Pageable
    ): Page<Product>

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.active = true")
    fun findByPriceBetweenAndActiveTrue(
        @Param("minPrice") minPrice: BigDecimal,
        @Param("maxPrice") maxPrice: BigDecimal,
        pageable: Pageable
    ): Page<Product>

    fun countByActiveTrue(): Long

    fun countByCategory(category: Category): Long
}