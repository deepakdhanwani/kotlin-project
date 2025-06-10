package com.example.upworkkotlin.dtos

import com.example.upworkkotlin.models.Image
import com.example.upworkkotlin.models.Option
import com.example.upworkkotlin.models.Variant
import java.util.*

data class ProductDto(
    var id: Long? = null,
    var title: String? = null,
    var handle: String? = null,
    var bodyHtml: String? = null,
    var publishedAt: Date? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var vendor: String? = null,
    var productType: String? = null,
    var tags: MutableList<String> = mutableListOf(),
    var variants: MutableList<Variant> = mutableListOf(),
    var images: MutableList<Image> = mutableListOf(),
    var options: MutableList<Option> = mutableListOf()
)
