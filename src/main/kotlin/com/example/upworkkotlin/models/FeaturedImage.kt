package com.example.upworkkotlin.models

import jakarta.persistence.*
import java.util.*

@Entity
data class FeaturedImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "product_id")
    var productId: Long? = null,

    var position: Int = 0,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    var createdAt: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    var updatedAt: Date? = null,

    var alt: String? = null,
    var width: Int = 0,
    var height: Int = 0,
    var src: String? = null,

    @ElementCollection
    @CollectionTable(name = "featured_image_variant_ids")
    @Column(name = "variant_id")
    var variantIds: MutableList<Long> = mutableListOf()
)
