package com.example.upworkkotlin.models

import jakarta.persistence.*
import java.util.*

@Entity
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    var createdAt: Date? = null,

    var position: Int = 0,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    var updatedAt: Date? = null,

    @Column(name = "product_id")
    var productId: Long? = null,

    @ElementCollection
    @CollectionTable(name = "image_variant_ids")
    @Column(name = "variant_id")
    var variantIds: MutableList<Long> = mutableListOf(),

    var src: String? = null,
    var width: Int = 0,
    var height: Int = 0
)
