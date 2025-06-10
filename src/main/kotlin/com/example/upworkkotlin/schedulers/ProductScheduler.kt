package com.example.upworkkotlin.schedulers

import com.example.upworkkotlin.dtos.ProductDto
import com.example.upworkkotlin.repositories.ProductRepository
import com.example.upworkkotlin.services.ProductService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import kotlin.collections.get

@Component
class ProductScheduler(
    private val productService: ProductService,
    private val productRepository: ProductRepository,
    private val restTemplate: RestTemplate
) {

    /**
     * Scheduled task that runs only once at startup with initialDelay=0
     * The fixedDelay is set to Long.MAX_VALUE to ensure it doesn't run again
     */
    @Scheduled(initialDelay = 0, fixedDelay = Long.MAX_VALUE)
    fun fetchAndSaveProducts() {
        println("Starting product fetch from https://famme.no/products.json")

        try {
            // Fetch products from the URL
            val response = restTemplate.getForObject("https://famme.no/products.json", Map::class.java)

            if (response != null && response.containsKey("products")) {
                @Suppress("UNCHECKED_CAST")
                val products = response["products"] as List<Map<String, Any>>

                println("Fetched ${products.size} products from API")

                // Process each product
                for (productData in products) {
                    try {
                        // Convert the product data to a ProductDto
                        val productDto = convertToProductDto(productData)

                        // Check if the product has an ID
                        if (productDto.id != null) {
                            // Check if a product with the same ID already exists
                            val productExists = productRepository.existsById(productDto.id!!)

                            if (productExists) {
                                // Skip this product as it already exists
                                println("Skipping product with ID ${productDto.id} as it already exists")
                                continue
                            }
                        }

                        // Save the product to the database
                        productService.createProduct(productDto)
                        println("Saved product with ID ${productDto.id}")
                    } catch (e: Exception) {
                        System.err.println("Error processing product: ${e.message}")
                    }
                }

                println("Finished importing products")
            } else {
                System.err.println("Invalid response format from API")
            }
        } catch (e: Exception) {
            System.err.println("Error fetching products: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun convertToProductDto(productData: Map<String, Any>): ProductDto {
        // Create a new ProductDto and populate it with data from the API
        val productDto = ProductDto()

        // Set the product ID from the API response
        if (productData.containsKey("id") && productData["id"] != null) {
            // The ID might be a Number (Integer, Long) or a String, so we need to handle both cases
            val idObj = productData["id"]
            when (idObj) {
                is Number -> productDto.id = idObj.toLong()
                is String -> try {
                    productDto.id = idObj.toLong()
                } catch (e: NumberFormatException) {
                    System.err.println("Error parsing product ID: ${e.message}")
                }
            }
        }

        // Set basic product properties
        productDto.title = productData["title"] as? String
        productDto.handle = productData["handle"] as? String
        productDto.bodyHtml = productData["body_html"] as? String
        productDto.vendor = productData["vendor"] as? String
        productDto.productType = productData["product_type"] as? String

        // Handle dates if needed
        // Note: You might need to convert string dates to Date objects

        // Handle tags
        if (productData.containsKey("tags") && productData["tags"] is List<*>) {
            @Suppress("UNCHECKED_CAST")
            val tags = productData["tags"] as? List<String>
            if (tags != null) {
                productDto.tags = tags.toMutableList()
            }
        }

        // Handle variants, images, and options if needed
        // This would require more complex mapping logic

        return productDto
    }
}