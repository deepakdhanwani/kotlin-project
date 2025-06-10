package com.example.upworkkotlin.services

import com.example.upworkkotlin.dtos.ProductDto
import com.example.upworkkotlin.exceptions.ProductNotFoundException
import com.example.upworkkotlin.models.Product
import com.example.upworkkotlin.repositories.ProductRepository
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<ProductDto> {
        val products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "title"))
        return products.map { convertToDto(it) }
    }

    fun getProductsPage(page: Int, size: Int): Page<ProductDto> {
        // Sort by updatedAt in descending order
        val sort = Sort.by(Sort.Direction.DESC, "title")
        val pageable = PageRequest.of(page, size, sort)
        val productPage = productRepository.findAll(pageable)
        return productPage.map { convertToDto(it) }
    }

    fun getProductById(id: Long): ProductDto {
        val product = productRepository.findById(id)
            .orElseThrow { ProductNotFoundException("Product not found with id: $id") }
        return convertToDto(product)
    }

    fun createProduct(productDto: ProductDto): ProductDto {
        val product = convertToEntity(productDto)

        // Generate a unique ID if not provided
        if (product.id == null) {
            // Get the maximum ID from the repository and increment by 1
            val maxId = productRepository.findMaxId()
            product.id = maxId?.plus(1) ?: 1L
        }

        val savedProduct = productRepository.save(product)
        return convertToDto(savedProduct)
    }

    fun updateProduct(id: Long, productDto: ProductDto): ProductDto {
        val existingProduct = productRepository.findById(id)
            .orElseThrow { ProductNotFoundException("Product not found with id: $id") }

        // Update the existing product with values from the DTO
        BeanUtils.copyProperties(productDto, existingProduct, "id")

        val updatedProduct = productRepository.save(existingProduct)
        return convertToDto(updatedProduct)
    }

    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw ProductNotFoundException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }

    private fun convertToDto(product: Product): ProductDto {
        val productDto = ProductDto()
        BeanUtils.copyProperties(product, productDto)
        return productDto
    }

    private fun convertToEntity(productDto: ProductDto): Product {
        val product = Product()
        BeanUtils.copyProperties(productDto, product)
        return product
    }
}
