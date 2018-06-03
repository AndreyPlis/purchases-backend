package com.purchases.backend.repository

import com.purchases.backend.model.Favorites
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoritesRepository : JpaRepository<Favorites, String>
