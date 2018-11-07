package com.purchases.backend.controller

import com.purchases.backend.model.Category
import com.purchases.backend.repository.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/categories")
class CategoryController(val categoryService: CategoryRepository) {


    @GetMapping
    @ResponseBody
    fun retrieveAllGoods(): List<Category> {
        return categoryService.findRootCategories()
    }

    @GetMapping("/{name}")
    @ResponseBody
    fun retrieveGood(@PathVariable name: String): ResponseEntity<Any> {
        val goodOptional = categoryService.findById(name)

        return if (goodOptional.isPresent)
            ResponseEntity(goodOptional.get(), HttpStatus.OK)
        else
            ResponseEntity.notFound().build()
    }


    @PostMapping
    fun createGood(@RequestBody category: Category): ResponseEntity<Any> {
        val newGood = categoryService.save(category)

        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newGood.name).toUri()

        return ResponseEntity.created(location).build()

    }
}