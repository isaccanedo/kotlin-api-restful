package com.kotlin.api.controller

import com.kotlin.api.dto.ProductRequestDto
import com.kotlin.api.dto.ProductResponseDto
import com.kotlin.api.dto.ProductUpdateDto
import com.kotlin.api.entity.Category
import com.kotlin.api.service.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = ["*"])
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun createProduct(@Valid @RequestBody productRequest: ProductRequestDto): ResponseEntity<ProductResponseDto> {
        val createdProduct = productService.createProduct(productRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductResponseDto> {
        val product = productService.getProductById(id)
        return ResponseEntity.ok(product)
    }

    @GetMapping
    fun getAllProducts(
        @PageableDefault(size = 20) pageable: Pageable,
        @RequestParam(required = false) activeOnly: Boolean?
    ): ResponseEntity<Page<ProductResponseDto>> {
        val products = if (activeOnly == true) {
            productService.getActiveProducts(pageable)
        } else {
            productService.getAllProducts(pageable)
        }
        return ResponseEntity.ok(products)
    }

    @GetMapping("/category/{category}")
    fun getProductsByCategory(
        @PathVariable category: Category,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Page<ProductResponseDto>> {
        val products = productService.getProductsByCategory(category, pageable)
        return ResponseEntity.ok(products)
    }

    @GetMapping("/search")
    fun searchProductsByName(
        @RequestParam name: String,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Page<ProductResponseDto>> {
        val products = productService.searchProductsByName(name, pageable)
        return ResponseEntity.ok(products)
    }

    @GetMapping("/price-range")
    fun getProductsByPriceRange(
        @RequestParam minPrice: BigDecimal,
        @RequestParam maxPrice: BigDecimal,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Page<ProductResponseDto>> {
        val products = productService.getProductsByPriceRange(minPrice, maxPrice, pageable)
        return ResponseEntity.ok(products)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @Valid @RequestBody updateDto: ProductUpdateDto
    ): ResponseEntity<ProductResponseDto> {
        val updatedProduct = productService.updateProduct(id, updateDto)
        return ResponseEntity.ok(updatedProduct)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivateProduct(@PathVariable id: Long): ResponseEntity<ProductResponseDto> {
        val deactivatedProduct = productService.deactivateProduct(id)
        return ResponseEntity.ok(deactivatedProduct)
    }

    @GetMapping("/stats")
    fun getProductStats(): ResponseEntity<Map<String, Any>> {
        val stats = productService.getProductStats()
        return ResponseEntity.ok(stats)
    }
}