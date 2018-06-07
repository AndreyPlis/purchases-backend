package com.purchases.backend.controller

import com.purchases.backend.model.Good
import com.purchases.backend.service.GoodService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@RestController
@RequestMapping("/goods")
class GoodController(val goodService: GoodService) {


    @GetMapping
    @ResponseBody
    fun retrieveAllGoods(): List<Good> {
        return goodService.getAllGoods()
    }

    @GetMapping("/{name}")
    @ResponseBody
    fun retrieveGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = goodService.findGood(name)
        if (goodOptional.isPresent)
            return ResponseEntity(goodOptional.get(), HttpStatus.OK)
        else
            return ResponseEntity.notFound().build()
    }


    @PostMapping
    fun createGood(@RequestBody good: Good): ResponseEntity<Any> {
        val newGood = goodService.addGood(good)

        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newGood.name).toUri()

        return ResponseEntity.created(location).build()

    }

    @DeleteMapping("/{name}")
    fun deleteGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = goodService.findGood(name)

        if (!goodOptional.isPresent)
            return ResponseEntity.notFound().build()

        goodService.deleteGood(goodOptional.get())

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteAllGoods(): ResponseEntity<Any> {
        goodService.deleteAllGoods()
        return ResponseEntity.noContent().build()
    }
}