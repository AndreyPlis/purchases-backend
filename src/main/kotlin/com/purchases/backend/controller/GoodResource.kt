package com.purchases.backend.controller

import com.purchases.backend.model.Good
import com.purchases.backend.service.GoodService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/goods")
class GoodResource(val goodService: GoodService) {


    @GetMapping
    fun retrieveAllGoods(): List<Good> {
        return goodService.getAllGoods()
    }

    @GetMapping("/{name}")
    fun retrieveGood(@PathVariable name: String): Good {
        return goodService.findGood(name)
    }


    @PostMapping
    fun createGood(@RequestBody good: Good): ResponseEntity<Any> {
        val newGood = goodService.addGood(good)

        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newGood.name).toUri()

        return ResponseEntity.created(location).build()

    }

    @PutMapping("/{name}")
    fun updateGood(@RequestBody good: Good, @PathVariable name: String): ResponseEntity<Any> {

        /* //fixme
         val goodOptional = goodRepository!!.findById(name)

         if (!goodOptional.isPresent)
             return ResponseEntity.notFound().build()

         goodOptional.get().name = good.name

         goodRepository!!.save(goodOptional.get())*/

        return ResponseEntity.noContent().build()
    }
}