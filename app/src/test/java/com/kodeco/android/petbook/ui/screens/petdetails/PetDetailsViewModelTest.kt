package com.kodeco.android.petbook.ui.screens.petdetails

import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.PetDao
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.PetRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PetDetailsViewModelTest {
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
        petRepository = mock<PetRepository>()
    }

    @Test
    fun getPetDetailsTest(){
        whenever(petRepository.getPet(0, "feed")).thenReturn(petList[0])
        val petDetailsViewModel = PetDetailsViewModel(petRepository)
        val pet = petDetailsViewModel.getPetDetails(0, "feed")
        if (pet != null) {
            Assert.assertEquals(pet.id, petList[0].id)
        }
    }


}