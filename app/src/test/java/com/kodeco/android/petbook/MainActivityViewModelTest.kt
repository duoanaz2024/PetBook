package com.kodeco.android.petbook

import com.kodeco.android.petbook.repositories.DataManager
import com.kodeco.android.petbook.ui.screens.favorites.FavoritesViewModel
import com.kodeco.android.petbook.util.PetBookState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Test

class MainActivityViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun splashTest() = runTest {

        Dispatchers.setMain(Dispatchers.Unconfined)
        try{
            val viewModel = MainActivityViewModel()
            Assert.assertTrue("It is not loading", viewModel.isLoading.value)
        } finally {
            Dispatchers.resetMain()
        }



    }
}