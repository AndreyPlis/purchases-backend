package com.purchases.backend.repository

import com.purchases.backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findOneByUserId(userId: String): Optional<User>
    fun findOneByUserIdAndPassword(userId: String, password: String): Optional<User>
}

