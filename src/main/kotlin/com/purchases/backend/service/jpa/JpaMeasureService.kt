package com.purchases.backend.service.jpa

import com.purchases.backend.model.Measure
import com.purchases.backend.repository.MeasureRepository
import com.purchases.backend.service.MeasureService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
internal class JpaMeasureService(val measureRepository: MeasureRepository) : MeasureService {
    override fun findMeasure(name: String): Optional<Measure> {
        return measureRepository.findById(name)
    }

    override fun addMeasure(measure: Measure): Measure {
        return measureRepository.save(measure)
    }

    override fun deleteMeasure(measure: Measure) {
        measureRepository.delete(measure)
    }

    override fun deleteAllMeasures() {
        measureRepository.deleteAll()
    }

    override fun getAllMeasures(): List<Measure> {
        return measureRepository.findAll()
    }

}