package com.purchases.backend.service.jpa

import com.purchases.backend.repository.PurchasesRepository
import com.purchases.backend.service.PurchaseService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class JpaPurchasesService(val purchasesRepository: PurchasesRepository) : PurchaseService {

}