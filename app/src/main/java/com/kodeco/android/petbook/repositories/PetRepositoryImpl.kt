package com.kodeco.android.petbook.repositories

import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.PetDao
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take

object DataManager {

    var favorites = setOf<String>()
    val pets: MutableStateFlow<List<Pet>> = MutableStateFlow(emptyList())

}

class PetRepositoryImpl(private val apiService: RemoteApiService,
                            private val dao: PetDao, private val prefs: PetPrefs) : PetRepository {

    override val pets: StateFlow<List<Pet>> = DataManager.pets.asStateFlow()

    override suspend fun fetchPets() {

        try{
            val cacheEnabled = prefs.getLocalStorageEnabled().take(1).first()
            // var favorites: List<String> = DataManager.favorites.toList()
            var favorites: List<String> = emptyList()
            DataManager.favorites = setOf()
            if (cacheEnabled){
                favorites = dao.getFavorites(true)
                // DataManager.favorites = favorites.toSet()
            }
            DataManager.pets.value = try {
                val response = apiService.getCountries()
                if (response.isSuccessful) {
                    val petList = response.body()!!
                        .toMutableList()
                        .map { pet ->
                            pet.copy(isFavorite = favorites.contains(pet.commonName))
                        }
                    if (cacheEnabled){
                        dao.insertCountries(petList)
                    }
                    petList
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

    override fun getPet(index: Int): Pet? {
        return DataManager.pets.value.getOrNull(index)
    }

    override suspend fun favorite(pet: Pet) {
        val cacheEnabled = prefs.getLocalStorageEnabled().take(1).first()
        if (cacheEnabled){
            if (dao.getCountries().isEmpty()){
                dao.insertCountries(DataManager.pets.value)
            }
        }
        val favorites: List<String> = if (cacheEnabled){
            dao.getFavorites(true)
        } else {
            val mutableList = DataManager.favorites.toMutableList()
            if (mutableList.contains(pet.commonName)){
                mutableList.remove(pet.commonName)
            }
            else{
                mutableList.add(pet.commonName)
            }
            DataManager.favorites = mutableList.toSet()
            mutableList
        }
        var isFavorite = favorites.contains(pet.commonName)
        if (cacheEnabled){
            isFavorite = !isFavorite
            dao.updateCountry(pet.copy(isFavorite = isFavorite))
        }

        val index = DataManager.pets.value.indexOf(pet)
        val mutableCountries = DataManager.pets.value.toMutableList()
        mutableCountries[index] = mutableCountries[index].copy(isFavorite = isFavorite)
        DataManager.pets.value = mutableCountries.toList()
    }
}