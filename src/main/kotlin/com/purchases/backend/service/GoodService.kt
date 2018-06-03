package com.purchases.backend.service

import com.purchases.backend.model.Good


interface GoodService {

    fun getAllGoods(): List<Good>

    fun findGood(name: String): Good

    fun addGood(good: Good): Good
}
