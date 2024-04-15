package com.kodeco.android.petbook.networking.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kodeco.android.petbook.model.Pet

@Dao
interface PetDao {

    @Query("SELECT * FROM pets LIMIT 50")
    suspend fun getPets(): List<Pet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPets(pets: List<Pet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet)

    @Query("DELETE FROM pets")
    suspend fun deleteAllPets()

    @Query("SELECT id FROM pets where isFavorite = :isFavorite")
    suspend fun getFavorites(isFavorite: Boolean): List<String>

    @Query("SELECT * FROM pets where id = :id")
    suspend fun getPet(id: String): Pet

    @Query("SELECT * FROM pets where isFavorite = :isFavorite")
    suspend fun getAllFavorites(isFavorite: Boolean): List<Pet>

}