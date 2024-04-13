package com.kodeco.android.petbook.repositories

import com.kodeco.android.petbook.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {

    val pets: Flow<List<Pet>>

    suspend fun fetchPets()

    fun getPet(index: Int): Pet?

    suspend fun favorite(pet: Pet)
}