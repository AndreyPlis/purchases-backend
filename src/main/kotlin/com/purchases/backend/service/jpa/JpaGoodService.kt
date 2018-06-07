package com.purchases.backend.service.jpa

import com.purchases.backend.model.Good
import com.purchases.backend.repository.GoodRepository
import com.purchases.backend.service.GoodService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
internal class JpaGoodService(val goodRepository: GoodRepository) : GoodService {
    override fun deleteAllGoods() {
        goodRepository.deleteAll()
    }

    override fun deleteGood(good: Good) {
        goodRepository.delete(good);
    }

    override fun addGood(good: Good): Good {
        return goodRepository.save(good)
    }

    override fun findGood(name: String): Optional<Good> {
        return goodRepository.findById(name)
    }

    override fun getAllGoods(): List<Good> {
        return goodRepository.findAll()
    }

}