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

    @Query("SELECT * FROM countries")
    suspend fun getCountries(): List<Pet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<Pet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(pet: Pet)

    @Update
    suspend fun updateCountry(pet: Pet)

    @Delete
    suspend fun deleteCountry(pet: Pet)

    @Query("DELETE FROM countries")
    suspend fun deleteAllCountries()

    @Query("SELECT commonName FROM countries where isFavorite = :isFavorite")
    suspend fun getFavorites(isFavorite: Boolean): List<String>

    @Query("SELECT * FROM countries where commonName = :commonName")
    suspend fun getCountry(commonName: String): Pet


}