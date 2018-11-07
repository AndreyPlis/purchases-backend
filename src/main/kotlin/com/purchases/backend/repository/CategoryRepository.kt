package com.purchases.backend.repository

import com.purchases.backend.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, String> {

    @Query("select c from Category c where c.level = 0")
    fun findRootCategories(): List<Category>
}
