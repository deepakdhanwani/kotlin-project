package com.example.upworkkotlin.controllers

import com.example.upworkkotlin.dtos.ProductDto
import com.example.upworkkotlin.services.ProductService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import kotlin.collections.iterator

@Controller
@RequestMapping("/products")
class ProductWebController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(model: Model): String {
        // Don't load products initially, they will be loaded via HTMX
        // Add an empty ProductDto to the model for the form
        model.addAttribute("productDto", ProductDto())
        return "products"
    }

    @GetMapping("/load")
    fun loadProducts(model: Model): String {
        val products = productService.getAllProducts()
        model.addAttribute("products", products)
        return "products :: productsContainer"
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long, model: Model): String {
        val product = productService.getProductById(id)
        model.addAttribute("product", product)
        return "product-detail"
    }

    @PostMapping
    fun createProduct(productDto: ProductDto, redirectAttributes: RedirectAttributes): String {
        try {
            val createdProduct = productService.createProduct(productDto)
            redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!")
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating product: ${e.message}")
        }
        return "redirect:/products"
    }

    @PostMapping("/create-htmx")
    fun createProductHtmx(
        @ModelAttribute productDto: ProductDto,
        @RequestParam requestParams: Map<String, String>,
        model: Model,
        request: HttpServletRequest
    ): String {
        try {
            request.parameterMap.forEach { (k, v) -> println("$k = ${v.contentToString()}") }

            // Debug logging for request parameters
            println("Request parameters received:")
            for ((key, value) in requestParams) {
                println("$key: $value")
            }

            // Debug logging for the DTO
            println("Received ProductDto: $productDto")
            println("ProductDto.id: ${productDto.id}")
            println("ProductDto.createdAt: ${productDto.createdAt}")
            println("ProductDto.updatedAt: ${productDto.updatedAt}")

            // Ensure ID is set
            if (productDto.id == null) {
                println("ID is null, generating a new one")
                productDto.id = System.currentTimeMillis()
            }

            // Ensure dates are set
            if (productDto.createdAt == null) {
                println("createdAt is null, setting to current time")
                productDto.createdAt = Date()
            }

            if (productDto.updatedAt == null) {
                println("updatedAt is null, setting to current time")
                productDto.updatedAt = Date()
            }

            val createdProduct = productService.createProduct(productDto)
            println("Created product: $createdProduct")

            // Load the first page of products after creating a new one
            val productPage = productService.getAllProducts();
            model.addAttribute("products", productPage)

            // Return the products container fragment
            return "products :: productsContainer"
        } catch (e: Exception) {
            // Log the error
            System.err.println("Error creating product: ${e.message}")
            e.printStackTrace()

            // In case of error, return an error message
            model.addAttribute("errorMessage", "Error creating product: ${e.message}")
            return "products :: productsContainer"
        }
    }

    @GetMapping("/{id}/edit")
    fun showEditForm(@PathVariable id: Long, model: Model): String {
        val product = productService.getProductById(id)
        model.addAttribute("product", product)
        return "product-edit"
    }

    @PostMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        productDto: ProductDto,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            val updatedProduct = productService.updateProduct(id, productDto)
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!")
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product: ${e.message}")
        }
        return "redirect:/products"
    }

    @PostMapping("/{id}/delete")
    fun deleteProduct(@PathVariable id: Long, redirectAttributes: RedirectAttributes): String {
        try {
            productService.deleteProduct(id)
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!")
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: ${e.message}")
        }
        return "redirect:/products"
    }
}
