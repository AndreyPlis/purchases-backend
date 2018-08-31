package com.purchases.backend.controller

import com.purchases.backend.model.Measure
import com.purchases.backend.repository.MeasureRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/measures")
class MeasureController(val measureService: MeasureRepository) {

    @GetMapping
    @ResponseBody
    fun retrieveAllGoods(): List<Measure> {
        return measureService.findAll()
    }

    @GetMapping("/{name}")
    @ResponseBody
    fun retrieveGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = measureService.findById(name)
        if (goodOptional.isPresent)
            return ResponseEntity(goodOptional.get(), HttpStatus.OK)
        else
            return ResponseEntity.notFound().build()
    }


    @PostMapping
    fun createGood(@RequestBody measure: Measure): ResponseEntity<Any> {
        val newGood = measureService.save(measure)

        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newGood.name).toUri()

        return ResponseEntity.created(location).build()

    }

    @DeleteMapping("/{name}")
    fun deleteGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = measureService.findById(name)

        if (!goodOptional.isPresent)
            return ResponseEntity.notFound().build()

        measureService.delete(goodOptional.get())

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteAllGoods(): ResponseEntity<Any> {
        measureService.deleteAll()
        return ResponseEntity.noContent().build()
    }
}