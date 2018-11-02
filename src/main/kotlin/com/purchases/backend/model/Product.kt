package com.purchases.backend.model

import java.util.*
import javax.persistence.*

@Entity
data class Product(
        @Id
        val name: String,
        val price: Float,
        val price_begins_on: Date,
        val price_ends_on: Date,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        val category: Category
)