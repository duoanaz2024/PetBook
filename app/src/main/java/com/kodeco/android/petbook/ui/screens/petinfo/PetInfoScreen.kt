package com.kodeco.android.petbook.ui.screens.petinfo

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kodeco.android.petbook.ui.components.PetErrorScreen
import com.kodeco.android.petbook.ui.components.PetInfoList
import com.kodeco.android.petbook.ui.components.Loading
import com.kodeco.android.petbook.util.PetBookState

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PetInfoScreen(
    viewModel: PetInfoViewModel,
    onPetRowTap: (Any?) -> Unit,
    onSettingsTap: () -> Unit,
    aboutTap: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        val petState = viewModel.uiState.collectAsState()
        val favoritesEnabled = viewModel.pref.getFavoritesFeatureEnabled().collectAsState(initial = false)

        when (val curState = petState.value) {
            is PetBookState.Success -> {
                val petList = curState.pets
                PetInfoList(
                    favoritesEnabled=favoritesEnabled,
                    petList=petList,
                    onPetRowTap=onPetRowTap,
                    aboutTap=aboutTap,
                    onSettingsTap=onSettingsTap,
                    onFavorite={pet -> viewModel.favorite(pet)}
                ){
                    viewModel.refreshPets()
                }
            }
            is PetBookState.Error -> {
                PetErrorScreen(
                    headline = "Error",
                    subtitle = curState.message ?: "Something Went Wrong"
                ){
                    viewModel.refreshPets()
                }

            }
            is PetBookState.Loading -> {
                Loading(aboutTap=aboutTap){
                    viewModel.refreshPets()
                }

            }

        }

    }

}

