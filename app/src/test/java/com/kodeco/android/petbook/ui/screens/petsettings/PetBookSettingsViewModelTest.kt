package com.kodeco.android.petbook.ui.screens.petsettings

import android.util.Log
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.PetDao
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.repositories.PetRepositoryImpl
import com.kodeco.android.petbook.ui.screens.settings.PetBookSettingsViewModel
import com.kodeco.android.petbook.util.PetBookState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import net.bytebuddy.implementation.bytecode.Throw
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PetBookSettingsViewModelTest {

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

    @Test
    fun getFavoritesFeatureEnabledTest() = runTest{
        val viewModel = PetBookSettingsViewModel(prefs)
        whenever(prefs.getFavoritesFeatureEnabled()).thenReturn(flow { emit(true) })
        val enabled = viewModel.getFavoritesFeatureEnabled().first()
        Assert.assertTrue(enabled)
    }

    @Test
    fun toggleFavoritesFeatureTest() = runTest {
        val viewModel = PetBookSettingsViewModel(prefs)
        var ex = ""
        try{
            viewModel.toggleFavoritesFeature()
        } catch (e: Throwable) {
            ex = "exception"
        }
        Assert.assertEquals(ex, "")
    }

}