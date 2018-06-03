package com.purchases.backend.service.jpa

import com.purchases.backend.repository.MeasureRepository
import com.purchases.backend.service.MeasureService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class JpaMeasureService(val measureRepository: MeasureRepository) : MeasureService {

}