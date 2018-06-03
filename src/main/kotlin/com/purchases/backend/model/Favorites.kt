package com.purchases.backend.model

import javax.persistence.*


@Entity
data class Favorites(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,

        val name: String,

        @OneToMany(mappedBy = "favorites", cascade = [(CascadeType.ALL)])
        val purchases: Set<Purchase> = HashSet()
)