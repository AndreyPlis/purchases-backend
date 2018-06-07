package com.purchases.backend.service

import com.purchases.backend.model.Measure
import java.util.*

interface MeasureService {
    fun getAllMeasures(): List<Measure>

    fun findMeasure(name: String): Optional<Measure>

    fun addMeasure(measure: Measure): Measure

    fun deleteMeasure(measure: Measure)

    fun deleteAllMeasures()
}