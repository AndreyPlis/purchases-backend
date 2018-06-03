package com.purchases.backend.model

import javax.persistence.*

@Entity
data class Purchases(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val name: String,
        @OneToMany(mappedBy = "purchases", cascade = [(CascadeType.ALL)])
        val purchases: Set<Purchase> = HashSet()
)