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
    val petFavorites: MutableStateFlow<List<Pet>> = MutableStateFlow(emptyList())

}

class PetRepositoryImpl(private val apiService: RemoteApiService,
                            private val dao: PetDao, private val prefs: PetPrefs) : PetRepository {

    override val pets: StateFlow<List<Pet>> = DataManager.pets.asStateFlow()
    override val petFavorites: StateFlow<List<Pet>> = DataManager.petFavorites.asStateFlow()

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
                val response = apiService.getPets()
                if (response.isSuccessful) {
                    val petList = response.body()!!
                        .toMutableList()
                        .map { pet ->
                            pet.copy(isFavorite = favorites.contains(pet.id))
                        }
                    if (cacheEnabled){
                        dao.insertPets(petList)
                    }
                    petList
                } else {
                    if (cacheEnabled && dao.getPets().isNotEmpty()){
                        dao.getPets()
                    }
                    else{
                        throw Exception(response.message())
                    }
                }
            } catch (e: Exception) {
                if (cacheEnabled && dao.getPets().isNotEmpty()){
                    dao.getPets()
                }
                else{
                    throw Exception("${e.message}")
                }

            }
        } catch (e: Exception) {
            throw Exception("Request failed: ${e.message}")
        }


    }

    override fun getPet(index: Int, type: String): Pet? {
        return if (type == "feed"){
            DataManager.pets.value.getOrNull(index)
        } else{
            DataManager.petFavorites.value.getOrNull(index)
        }

    }

    override suspend fun favorite(pet: Pet) {
        val cacheEnabled = prefs.getLocalStorageEnabled().take(1).first()
        if (cacheEnabled){
            if (dao.getPets().isEmpty()){
                dao.insertPets(DataManager.pets.value)
            }
        }
        val favorites: List<String> = if (cacheEnabled){
            dao.getFavorites(true)
        } else {
            val mutableList = DataManager.favorites.toMutableList()
            if (mutableList.contains(pet.id)){
                mutableList.remove(pet.id)
            }
            else{
                mutableList.add(pet.id)
            }
            DataManager.favorites = mutableList.toSet()
            mutableList
        }
        var isFavorite = favorites.contains(pet.id)
        if (cacheEnabled){
            isFavorite = !isFavorite
            dao.updatePet(pet.copy(isFavorite = isFavorite))
        }

        val index = DataManager.pets.value.indexOf(pet)
        if (index != -1){
            val mutablePets = DataManager.pets.value.toMutableList()
            mutablePets[index] = mutablePets[index].copy(isFavorite = isFavorite)
            DataManager.pets.value = mutablePets.toList()
        }

        DataManager.petFavorites.value = try {
            if (cacheEnabled){
                dao.getAllFavorites(true)
            }
            else{
                throw Exception("Something Went Wrong")
            }
        } catch (e: Exception) {
            throw Exception("${e.message}")

        }
    }

    override suspend fun fetchFavorites() {

        try{
            val cacheEnabled = prefs.getLocalStorageEnabled().take(1).first()
            DataManager.petFavorites.value = try {
                if (cacheEnabled){
                    dao.getAllFavorites(true)
                }
                else{
                    throw Exception("Something Went Wrong")
                }
            } catch (e: Exception) {
                throw Exception("${e.message}")


            }
        } catch (e: Exception) {
            throw Exception("Request failed: ${e.message}")
        }


    }

}