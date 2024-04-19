package com.kodeco.android.petbook.repositories

import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.PetDao
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PetRepositoryImplTest {

    private lateinit var mockDao: PetDao
    private lateinit var mockApiService: RemoteApiService
    private lateinit var prefs: PetPrefs
    private lateinit var petRepositoryImpl: PetRepositoryImpl
    private val petList = listOf(
        Pet(
            id = "1",
            url = "https://",
            width = 100,
            height = 200,
            breedName = "Cat",
            temperament = "Lovely",
            origin = "US",
            description = "Lovely Cat For Pet Book",
            lifeSpan = "5",
            childFriendly = 5,
            energyLevel = 5,
            intelligence = 6,
            strangerFriendly = 5,
            wikipediaUrl = "https://"
        )
    )


    @Before
    fun setUp(){
        mockDao = mock<PetDao>()
        mockApiService = mock<RemoteApiService>()
        prefs = mock<PetPrefs>()
        petRepositoryImpl = PetRepositoryImpl(mockApiService,mockDao,prefs)
    }

    @Test
    fun getPetTest(){

        DataManager.pets.value = petList

        Assert.assertEquals(
            petRepositoryImpl.getPet(0, "feed")?.id ?: "", "1")

    }

    @Test
    fun setFavoriteTest() = runBlocking{

        whenever(mockDao.getPets()).thenReturn(petList)

        whenever(mockDao.getFavorites(true)).thenReturn(listOf())

        whenever(mockDao.getAllFavorites(true)).thenReturn(petList)
        petRepositoryImpl = PetRepositoryImpl(mockApiService,mockDao,prefs)

        petRepositoryImpl.favorite(petList[0])
        Assert.assertEquals(DataManager.petFavorites.value[0].id, "1")

    }

    @Test
    fun fetchFavoritesTest() = runBlocking {

        whenever(mockDao.getAllFavorites(true)).thenReturn(petList)

        petRepositoryImpl.fetchFavorites()

        Assert.assertEquals(DataManager.petFavorites.value[0].id, "1")

    }

    @Test
    fun fetchPetsTest() = runBlocking {

        whenever(mockDao.getPets()).thenReturn(petList)
        petRepositoryImpl.fetchPets()
        Assert.assertEquals(DataManager.pets.value[0].id, "1")

    }
}
