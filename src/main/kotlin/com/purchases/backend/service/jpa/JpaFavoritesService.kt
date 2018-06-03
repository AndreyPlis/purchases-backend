package com.purchases.backend.service.jpa

import com.purchases.backend.repository.FavoritesRepository
import com.purchases.backend.service.FavoritesService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class JpaFavoritesService(val favoritesRepository: FavoritesRepository) : FavoritesService {

}