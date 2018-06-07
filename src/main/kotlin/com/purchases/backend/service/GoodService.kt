package com.purchases.backend.service

import com.purchases.backend.model.Good
import java.util.*


interface GoodService {

    fun getAllGoods(): List<Good>

    fun findGood(name: String): Optional<Good>

    fun addGood(good: Good): Good

    fun deleteGood(good: Good)

    fun deleteAllGoods()
}
