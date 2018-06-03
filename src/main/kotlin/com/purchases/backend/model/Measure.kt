package com.purchases.backend.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Measure(
        @Id
        val name: String
)
