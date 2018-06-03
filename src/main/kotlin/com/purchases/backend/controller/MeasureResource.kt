package com.purchases.backend.controller

import com.purchases.backend.model.Measure
import com.purchases.backend.repository.MeasureRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MeasureResource {
    @Autowired
    private val measureRepository: MeasureRepository? = null

    val measures: List<Measure>
        @GetMapping("/measures")
        get() = measureRepository!!.findAll()
}