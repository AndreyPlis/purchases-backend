package com.purchases.backend.repository

import com.purchases.backend.model.Good
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GoodRepository : JpaRepository<Good, String>
