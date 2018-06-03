package com.purchases.backend.controller

import com.purchases.backend.model.Good
import com.purchases.backend.repository.GoodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/goods")
class GoodResource {
    @Autowired
    private val goodRepository: GoodRepository? = null

    @GetMapping
    fun retrieveAllGoods(): List<Good> {
        return goodRepository!!.findAll()
    }

    @GetMapping("/{name}")
    fun retrieveGood(@PathVariable name: String): Good {
        val good = goodRepository!!.findById(name)

        if (!good.isPresent)
            throw IllegalStateException("name-$name")

        return good.get()
    }


    @PostMapping
    fun createGood(@RequestBody good: Good): ResponseEntity<Any> {
        val goodStudent = goodRepository!!.save(good)

        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(goodStudent.name).toUri()

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