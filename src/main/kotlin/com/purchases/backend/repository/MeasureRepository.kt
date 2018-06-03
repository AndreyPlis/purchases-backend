package com.purchases.backend.repository

import com.purchases.backend.model.Measure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MeasureRepository : JpaRepository<Measure, String>