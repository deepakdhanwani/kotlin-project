package com.example.upworkkotlin.models

import jakarta.persistence.*

@Entity
data class Option(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    var name: String? = null,
    var position: Int = 0,
    
    @ElementCollection
    @CollectionTable(name = "option_values")
    @Column(name = "value")
    var values: MutableList<String> = mutableListOf()
)