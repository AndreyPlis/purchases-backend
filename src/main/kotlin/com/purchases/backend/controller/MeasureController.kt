package com.purchases.backend.controller

import com.purchases.backend.model.Measure
import com.purchases.backend.service.MeasureService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/measures")
class MeasureController(val measureService: MeasureService) {

    @GetMapping
    @ResponseBody
    fun retrieveAllGoods(): List<Measure> {
        return measureService.getAllMeasures()
    }

    @GetMapping("/{name}")
    @ResponseBody
    fun retrieveGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = measureService.findMeasure(name)
        if (goodOptional.isPresent)
            return ResponseEntity(goodOptional.get(), HttpStatus.OK)
        else
            return ResponseEntity.notFound().build()
    }


    @PostMapping
    fun createGood(@RequestBody measure: Measure): ResponseEntity<Any> {
        val newGood = measureService.addMeasure(measure)

        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newGood.name).toUri()

        return ResponseEntity.created(location).build()

    }

    @DeleteMapping("/{name}")
    fun deleteGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = measureService.findMeasure(name)

        if (!goodOptional.isPresent)
            return ResponseEntity.notFound().build()

        measureService.deleteMeasure(goodOptional.get())

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteAllGoods(): ResponseEntity<Any> {
        measureService.deleteAllMeasures()
        return ResponseEntity.noContent().build()
    }
}