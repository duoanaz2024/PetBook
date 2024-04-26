package com.kodeco.android.petbook.ui.screens.favorites

import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.PetDao
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.DataManager
import com.kodeco.android.petbook.repositories.PetRepositoryImpl
import com.kodeco.android.petbook.util.PetBookState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FavoritesViewModelTest {

    private lateinit var prefs: PetPrefs
    private lateinit var petRepositoryImpl: PetRepositoryImpl
    private lateinit var mockDao: PetDao
    private lateinit var mockApiService: RemoteApiService

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshPetsTest() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try{
            whenever(mockDao.getAllFavorites(true)).thenReturn(petList)
            val viewModel = FavoritesViewModel(petRepositoryImpl, prefs)
            viewModel.refreshPets()
            val uiState = viewModel.uiState.value as PetBookState.Success
            Assert.assertEquals(uiState.pets, petList)
        } finally {
            Dispatchers.resetMain()
        }


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun favoritePetTest() = runTest {
        whenever(mockDao.getPets()).thenReturn(petList)
        whenever(mockDao.getFavorites(true)).thenReturn(listOf())
        whenever(mockDao.getAllFavorites(true)).thenReturn(petList)
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try{
            val viewModel = FavoritesViewModel(petRepositoryImpl, prefs)
            DataManager.petFavorites.value = petList
            launch {viewModel.favorite(petList[0]) }
            advanceUntilIdle()
            val uiState = viewModel.uiState.value as PetBookState.Success
            Assert.assertEquals(uiState.pets, petList)
        } finally {
            Dispatchers.resetMain()
        }


    }


}