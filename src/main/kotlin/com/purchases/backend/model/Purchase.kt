package com.purchases.backend.model

import javax.persistence.*

@Entity
data class Purchase(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,

        val count: Float,

        @ManyToOne(cascade = [(CascadeType.ALL)])
        @JoinColumn(name = "goodId")
        val good : Good,

        @ManyToOne(cascade = [(CascadeType.ALL)])
        @JoinColumn(name = "measureId")
        val measure: Measure,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "favorites_id")
        val favorites: Favorites,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "purchases_id")
        val purchases: Purchases
)