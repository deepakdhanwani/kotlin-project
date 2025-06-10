package com.example.upworkkotlin.models

import jakarta.persistence.*
import java.util.*

@Entity
data class Product(
    @Id
    var id: Long? = null,

    var title: String? = null,

    var handle: String? = null,

    @Column(name = "body_html", columnDefinition = "TEXT")
    var bodyHtml: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "published_at")
    var publishedAt: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    var createdAt: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    var updatedAt: Date? = null,

    var vendor: String? = null,

    @Column(name = "product_type")
    var productType: String? = null,

    @ElementCollection
    @CollectionTable(name = "product_tags")
    @Column(name = "tag")
    var tags: MutableList<String> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "product_id")
    var variants: MutableList<Variant> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "product_id")
    var images: MutableList<Image> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "product_id")
    var options: MutableList<Option> = mutableListOf()
)
