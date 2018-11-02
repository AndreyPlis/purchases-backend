package com.purchases.backend.controller

import com.purchases.backend.model.Product
import com.purchases.backend.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@RestController
@RequestMapping("/products")
class ProductController(val productService: ProductRepository) {


    @GetMapping
    @ResponseBody
    fun retrieveAllGoods(): List<Product> {
        return productService.findAll()
    }

    @GetMapping("/{name}")
    @ResponseBody
    fun retrieveGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = productService.findById(name)

        return if (goodOptional.isPresent)
            ResponseEntity(goodOptional.get(), HttpStatus.OK)
        else
            ResponseEntity.notFound().build()
    }


    @PostMapping
    fun createGood(@RequestBody product: Product): ResponseEntity<Any> {
        val newGood = productService.save(product)

        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newGood.name).toUri()

        return ResponseEntity.created(location).build()

    }

    @DeleteMapping("/{name}")
    fun deleteGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = productService.findById(name)

        if (!goodOptional.isPresent)
            return ResponseEntity.notFound().build()

        productService.delete(goodOptional.get())

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteAllGoods(): ResponseEntity<Any> {
        productService.deleteAll()
        return ResponseEntity.noContent().build()
    }
}