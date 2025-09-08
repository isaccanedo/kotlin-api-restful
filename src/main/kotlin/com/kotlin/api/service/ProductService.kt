package com.kotlin.api.service

import com.kotlin.api.dto.ProductRequestDto
import com.kotlin.api.dto.ProductResponseDto
import com.kotlin.api.dto.ProductUpdateDto
import com.kotlin.api.entity.Category
import com.kotlin.api.exception.ProductNotFoundException
import com.kotlin.api.mapper.ProductMapper
import com.kotlin.api.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {

    fun createProduct(productRequest: ProductRequestDto): ProductResponseDto {
        val product = productMapper.toEntity(productRequest)
        val savedProduct = productRepository.save(product)
        return productMapper.toDto(savedProduct)
    }

    @Transactional(readOnly = true)
    fun getProductById(id: Long): ProductResponseDto {
        val product = productRepository.findById(id)
            .orElseThrow { ProductNotFoundException("Produto com ID $id não encontrado") }
        return productMapper.toDto(product)
    }

    @Transactional(readOnly = true)
    fun getAllProducts(pageable: Pageable): Page<ProductResponseDto> {
        return productRepository.findAll(pageable)
            .map { productMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    fun getActiveProducts(pageable: Pageable): Page<ProductResponseDto> {
        return productRepository.findByActiveTrue(pageable)
            .map { productMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    fun getProductsByCategory(category: Category, pageable: Pageable): Page<ProductResponseDto> {
        return productRepository.findByCategoryAndActiveTrue(category, pageable)
            .map { productMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    fun searchProductsByName(name: String, pageable: Pageable): Page<ProductResponseDto> {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(name, pageable)
            .map { productMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    fun getProductsByPriceRange(
        minPrice: BigDecimal,
        maxPrice: BigDecimal,
        pageable: Pageable
    ): Page<ProductResponseDto> {
        return productRepository.findByPriceBetweenAndActiveTrue(minPrice, maxPrice, pageable)
            .map { productMapper.toDto(it) }
    }

    fun updateProduct(id: Long, updateDto: ProductUpdateDto): ProductResponseDto {
        val existingProduct = productRepository.findById(id)
            .orElseThrow { ProductNotFoundException("Produto com ID $id não encontrado") }

        val updatedProduct = existingProduct.copy(
            name = updateDto.name ?: existingProduct.name,
            description = updateDto.description ?: existingProduct.description,
            price = updateDto.price ?: existingProduct.price,
            stock = updateDto.stock ?: existingProduct.stock,
            category = updateDto.category ?: existingProduct.category,
            active = updateDto.active ?: existingProduct.active,
            updatedAt = LocalDateTime.now()
        )

        val savedProduct = productRepository.save(updatedProduct)
        return productMapper.toDto(savedProduct)
    }

    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw ProductNotFoundException("Produto com ID $id não encontrado")
        }
        productRepository.deleteById(id)
    }

    fun deactivateProduct(id: Long): ProductResponseDto {
        return updateProduct(id, ProductUpdateDto(
            name = null,
            description = null,
            price = null,
            stock = null,
            category = null,
            active = false
        ))
    }

    @Transactional(readOnly = true)
    fun getProductStats(): Map<String, Any> {
        val totalProducts = productRepository.count()
        val activeProducts = productRepository.countByActiveTrue()
        val categoryStats = Category.values().associate { category ->
            category.name to productRepository.countByCategory(category)
        }

        return mapOf(
            "totalProducts" to totalProducts,
            "activeProducts" to activeProducts,
            "inactiveProducts" to (totalProducts - activeProducts),
            "categoryStats" to categoryStats
        )
    }
}