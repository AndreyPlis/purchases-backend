package com.purchases.backend.service.jpa

import com.purchases.backend.repository.PurchaseRepository
import com.purchases.backend.service.PurchaseService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class JpaPurchaseService(val purchaseRepository: PurchaseRepository) : PurchaseService {

}