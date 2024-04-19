package com.kodeco.android.petbook.ui.screens.petinfo

import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.PetDao
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.DataManager
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.repositories.PetRepositoryImpl
import com.kodeco.android.petbook.util.PetBookState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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

class PetInfoViewModelTest {

    private lateinit var prefs: PetPrefs
    private lateinit var petRepository: PetRepository
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
        petRepository = PetRepositoryImpl(mockApiService,mockDao,prefs)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshPetsTest() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try{
            whenever(mockDao.getPets()).thenReturn(petList)

            whenever(mockDao.getFavorites(true)).thenReturn(listOf())

            whenever(mockDao.getAllFavorites(true)).thenReturn(petList)
            val viewModel = PetInfoViewModel(petRepository, prefs)
            viewModel.refreshPets()
            val uiState = viewModel.uiState.value as PetBookState.Success
            Assert.assertEquals(uiState.pets, petList)
        } finally {
            Dispatchers.resetMain()
        }


    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshPetsErrorTest() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try{
            whenever(mockDao.getPets()).thenReturn(listOf())

            whenever(mockDao.getFavorites(true)).thenReturn(listOf())

            whenever(mockDao.getAllFavorites(true)).thenReturn(listOf())
            val viewModel = PetInfoViewModel(petRepository, prefs)
            viewModel.refreshPets()
            val uiState = viewModel.uiState.value as PetBookState.Error
            Assert.assertEquals(uiState.message,
                "Request failed: Cannot invoke" +
                        " \"retrofit2.Response.isSuccessful()\"" +
                        " because \"response\" is null")
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
            val viewModel = PetInfoViewModel(petRepository, prefs)
            DataManager.petFavorites.value = petList
            launch {viewModel.favorite(petList[0]) }
            advanceUntilIdle()
            val uiState = viewModel.uiState.value as PetBookState.Success
            Assert.assertEquals(uiState.pets.get(0).id, petList[0].id)
        } finally {
            Dispatchers.resetMain()
        }


    }
}