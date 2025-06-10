package com.example.upworkkotlin.repositories

import com.example.upworkkotlin.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    @Query("SELECT MAX(p.id) FROM Product p")
    fun findMaxId(): Long?
}