package com.kodeco.android.petbook.repositories

import com.kodeco.android.petbook.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {

    val pets: Flow<List<Pet>>
    val petFavorites: Flow<List<Pet>>

    suspend fun fetchPets()

    suspend fun fetchFavorites()

    fun getPet(index: Int, type: String): Pet?

    suspend fun favorite(pet: Pet)

}