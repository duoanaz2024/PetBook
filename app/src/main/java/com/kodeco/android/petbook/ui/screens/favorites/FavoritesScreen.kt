package com.kodeco.android.petbook.ui.screens.favorites

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.ui.components.FavoritesList
import com.kodeco.android.petbook.ui.components.LinearLoading
import com.kodeco.android.petbook.ui.components.PetErrorScreen
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme
import com.kodeco.android.petbook.util.PetBookState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onPetRowTap: (Any?) -> Unit,
    onNavigateUp: () -> Unit
) {

    val petState = viewModel.uiState.collectAsState()
    val favoritesEnabled = viewModel.pref.getFavoritesFeatureEnabled().collectAsState(initial = false)

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,

            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Favorites",
                            style = TextStyle(
                                fontSize = 25.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.Bold
                            ))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            onNavigateUp()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    }
                )
            }, content = {

                when (val curState = petState.value) {
                    is PetBookState.Success -> {
                        val petList = curState.pets
                        FavoritesList(
                            petList=petList,
                            favoritesEnabled=favoritesEnabled,
                            onPetRowTap=onPetRowTap,
                            onFavorite={pet -> viewModel.favorite(pet)}
                        )
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
            })

    }




}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true)
@Composable
fun PreviewFavoritesScreen(){
    MyApplicationTheme {
        FavoritesScreen(viewModel = FavoritesViewModel(
            repository = object : PetRepository {
                override val pets: Flow<List<Pet>>
                    get() = MutableStateFlow(listOf(Pet(
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
                    ))).asStateFlow()
                override val petFavorites: Flow<List<Pet>>
                    get() = MutableStateFlow(listOf(Pet(
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
                    ))).asStateFlow()

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

            }
        ),
            onPetRowTap = {}) {

        }
    }
}
