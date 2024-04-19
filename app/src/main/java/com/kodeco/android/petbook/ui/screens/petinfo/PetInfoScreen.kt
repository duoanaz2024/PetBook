package com.kodeco.android.petbook.ui.screens.petinfo

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.ui.components.LinearLoading
import com.kodeco.android.petbook.ui.components.PetErrorScreen
import com.kodeco.android.petbook.ui.components.PetInfoList
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme
import com.kodeco.android.petbook.util.PetBookState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PetInfoScreen(
    viewModel: PetInfoViewModel,
    onPetRowTap: (Any?) -> Unit,
    onSettingsTap: () -> Unit,
    onHeartTap: () -> Unit,
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
                if (petList.isEmpty()){
                    PetErrorScreen(
                        headline = "Error",
                        subtitle = "Something Went Wrong, Feed Unavailable"
                    ){
                        viewModel.refreshPets()
                    }
                }
                else{
                    PetInfoList(
                        favoritesEnabled=favoritesEnabled,
                        petList=petList,
                        onPetRowTap=onPetRowTap,
                        aboutTap=aboutTap,
                        onSettingsTap=onSettingsTap,
                        onHeartTap = onHeartTap,
                        onFavorite={pet -> viewModel.favorite(pet)}
                    ){
                        viewModel.refreshPets()
                    }
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
                LinearLoading()

            }

        }

    }

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true)
@Composable
fun PreviewPetInfoScreen(){
    MyApplicationTheme {
        PetInfoScreen(
            viewModel = PetInfoViewModel(repository = object : PetRepository {
                override val pets: Flow<List<Pet>>
                    get() = MutableStateFlow(listOf(
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
                    )).asStateFlow()
                override val petFavorites: Flow<List<Pet>>
                    get() = MutableStateFlow(listOf(
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
                    )).asStateFlow()

                override suspend fun fetchPets() {
                }

                override suspend fun fetchFavorites() {
                }

                override fun getPet(index: Int, type: String): Pet? {
                    return null
                }

                override suspend fun favorite(pet: Pet) {
                }

            },
                pref = object : PetPrefs {
                    override fun getLocalStorageEnabled(): Flow<Boolean> {
                        return MutableStateFlow(true).asStateFlow()
                    }

                    override fun getFavoritesFeatureEnabled(): Flow<Boolean> {
                        return MutableStateFlow(true).asStateFlow()
                    }

                    override suspend fun toggleLocalStorage() {
                    }

                    override suspend fun toggleFavoritesFeature() {
                    }

                }),
            onPetRowTap = {},
            onSettingsTap = {},
            onHeartTap = {}) {

        }
    }
}