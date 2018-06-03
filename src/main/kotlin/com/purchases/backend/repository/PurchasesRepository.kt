package com.purchases.backend.repository

import com.purchases.backend.model.Purchases
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchasesRepository : JpaRepository<Purchases, String>