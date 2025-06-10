package com.example.upworkkotlin.controllers

import com.example.upworkkotlin.dtos.ProductDto
import com.example.upworkkotlin.services.ProductService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val productService: ProductService) {

    @GetMapping("/")
    fun home(model: Model): String {
        // Don't load products initially, they will be loaded via HTMX
        // Add an empty ProductDto to the model for the form
        model.addAttribute("productDto", ProductDto())
        return "products"
    }
}