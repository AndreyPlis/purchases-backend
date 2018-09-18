package com.purchases.backend.model.user

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
data class User(
        @Id
        val name: String,
        val password: String,

        @Enumerated(EnumType.STRING)
        val role: Role
)