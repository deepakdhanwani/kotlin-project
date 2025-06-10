package com.example.upworkkotlin.models

import jakarta.persistence.*
import java.util.*

@Entity
data class Variant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String? = null,
    var option1: String? = null,
    var option2: String? = null,
    var option3: String? = null,
    var sku: String? = null,

    @Column(name = "requires_shipping")
    var requiresShipping: Boolean = false,

    var taxable: Boolean = false,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "featured_image_id")
    var featuredImage: FeaturedImage? = null,

    var available: Boolean = false,
    var price: String? = null,
    var grams: Int = 0,

    @Column(name = "compare_at_price")
    var compareAtPrice: String? = null,

    var position: Int = 0,

    @Column(name = "product_id")
    var productId: Long? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    var createdAt: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    var updatedAt: Date? = null
)
