package com.purchases.backend.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import java.util.*
import javax.persistence.*

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val name: String,
        val price: Float,
        val price_begins_on: Date?,
        val price_ends_on: Date?,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "PRODUCT_CATEGORY", joinColumns = arrayOf(JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")), inverseJoinColumns = arrayOf(JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")))
        @JsonIgnore
        val categories: MutableList<Category>
)