package com.purchases.backend.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Good(
        @Id
        val name: String
)