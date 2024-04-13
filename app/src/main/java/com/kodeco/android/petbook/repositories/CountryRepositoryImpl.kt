package com.kodeco.android.petbook.repositories

import android.util.Log
import com.kodeco.android.petbook.model.Country
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.CountryDao
import com.kodeco.android.petbook.networking.preferences.CountryPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take

object DataManager {

    var favorites = setOf<String>()
    val countries: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())

}

class CountryRepositoryImpl(private val apiService: RemoteApiService,
                            private val dao: CountryDao,private val prefs: CountryPrefs) : CountryRepository {

    override val countries: StateFlow<List<Country>> = DataManager.countries.asStateFlow()

    override suspend fun fetchCountries() {

        try{
            val cacheEnabled = prefs.getLocalStorageEnabled().take(1).first()
            // var favorites: List<String> = DataManager.favorites.toList()
            var favorites: List<String> = emptyList()
            DataManager.favorites = setOf()
            if (cacheEnabled){
                favorites = dao.getFavorites(true)
                // DataManager.favorites = favorites.toSet()
            }
            DataManager.countries.value = try {
                val response = apiService.getCountries()
                if (response.isSuccessful) {
                    val countryList = response.body()!!
                        .toMutableList()
                        .map { country ->
                            country.copy(isFavorite = favorites.contains(country.commonName))
                        }
                    if (cacheEnabled){
                        dao.insertCountries(countryList)
                    }
                    countryList
                } else {
                    if (cacheEnabled && dao.getCountries().isNotEmpty()){
                        dao.getCountries()
                    }
                    else{
                        throw Exception(response.message())
                    }
                }
            } catch (e: Exception) {
                if (cacheEnabled && dao.getCountries().isNotEmpty()){
                    dao.getCountries()
                }
                else{
                    throw Exception("${e.message}")
                }

            }
        } catch (e: Exception) {
            throw Exception("Request failed: ${e.message}")
        }


    }

    override fun getCountry(index: Int): Country? {
        return DataManager.countries.value.getOrNull(index)
    }

    override suspend fun favorite(country: Country) {
        val cacheEnabled = prefs.getLocalStorageEnabled().take(1).first()
        if (cacheEnabled){
            if (dao.getCountries().isEmpty()){
                dao.insertCountries(DataManager.countries.value)
            }
        }
        val favorites: List<String> = if (cacheEnabled){
            dao.getFavorites(true)
        } else {
            val mutableList = DataManager.favorites.toMutableList()
            if (mutableList.contains(country.commonName)){
                mutableList.remove(country.commonName)
            }
            else{
                mutableList.add(country.commonName)
            }
            DataManager.favorites = mutableList.toSet()
            mutableList
        }
        var isFavorite = favorites.contains(country.commonName)
        if (cacheEnabled){
            isFavorite = !isFavorite
            dao.updateCountry(country.copy(isFavorite = isFavorite))
        }

        val index = DataManager.countries.value.indexOf(country)
        val mutableCountries = DataManager.countries.value.toMutableList()
        mutableCountries[index] = mutableCountries[index].copy(isFavorite = isFavorite)
        DataManager.countries.value = mutableCountries.toList()
    }
}