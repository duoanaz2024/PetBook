package com.kodeco.android.petbook.ui.screens.petdetails

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.ui.components.PetDetails
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class MapState {
    Shrunk,
    Expanded
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetDetailsScreen(
    petId: Int,
    type: String,
    viewModel: PetDetailsViewModel,
    onNavigateUp: () -> Unit) {
    val pet = viewModel.getPetDetails(petId, type)

    if (pet != null){
        
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,

                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = pet.breedName,
                                color = MaterialTheme.colorScheme.onSurface)
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
                    PetDetails(pet = pet)
                })

        }

    }
    else{
        Log.d("INFO", "Pet Object is Null")
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewPetDetailsScreen(){
    MyApplicationTheme {
        PetDetailsScreen(
            petId = 0,
            type = "feed",
            viewModel = PetDetailsViewModel(repository = object : PetRepository {
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

                override fun getPet(index: Int, type: String): Pet {
                    return Pet(
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
                }

                override suspend fun favorite(pet: Pet) {
                }

            })) {
            
        }
    }
}
