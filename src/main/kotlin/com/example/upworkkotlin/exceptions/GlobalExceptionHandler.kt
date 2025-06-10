package com.example.upworkkotlin.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(ex: ProductNotFoundException, request: WebRequest): ResponseEntity<Map<String, Any>> {
        val errorDetails = HashMap<String, Any>()
        errorDetails["timestamp"] = Date()
        errorDetails["message"] = ex.message ?: "Product not found"
        errorDetails["details"] = request.getDescription(false)
        
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }
    
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<Map<String, Any>> {
        val errorDetails = HashMap<String, Any>()
        errorDetails["timestamp"] = Date()
        errorDetails["message"] = ex.message ?: "An error occurred"
        errorDetails["details"] = request.getDescription(false)
        
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}