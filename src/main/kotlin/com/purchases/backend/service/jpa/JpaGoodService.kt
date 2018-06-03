package com.purchases.backend.service.jpa

import com.purchases.backend.model.Good
import com.purchases.backend.repository.GoodRepository
import com.purchases.backend.service.GoodService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class JpaGoodService(val goodRepository: GoodRepository) : GoodService {

    override fun addGood(good: Good) : Good {
        return goodRepository.save(good)
    }

    override fun findGood(name: String): Good {
        val good = goodRepository.findById(name)

        if (!good.isPresent)
            throw IllegalStateException("not found good-$name")

        return good.get()
    }

    override fun getAllGoods(): List<Good> {
        return goodRepository.findAll()
    }

}