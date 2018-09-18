package com.purchases.backend.repository

import com.purchases.backend.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findOneByName(name: String): Optional<User>
    fun findOneByNameAndPassword(name: String, password: String): Optional<User>
}

