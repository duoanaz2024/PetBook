package com.kodeco.android.petbook.networking.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kodeco.android.petbook.model.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries")
    suspend fun getCountries(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Update
    suspend fun updateCountry(country: Country)

    @Delete
    suspend fun deleteCountry(country: Country)

    @Query("DELETE FROM countries")
    suspend fun deleteAllCountries()

    @Query("SELECT commonName FROM countries where isFavorite = :isFavorite")
    suspend fun getFavorites(isFavorite: Boolean): List<String>

    @Query("SELECT * FROM countries where commonName = :commonName")
    suspend fun getCountry(commonName: String): Country


}