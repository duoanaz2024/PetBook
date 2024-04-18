package com.kodeco.android.petbook.ui.screens.settings

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetBookSettingsScreen(
    viewModel: PetBookSettingsViewModel,
    onClick: () -> Unit) {

    var toggleFav = viewModel.getFavoritesFeatureEnabled().collectAsState(initial = false).value
    // var cacheEnabled = viewModel.getLocalStorageEnabled().collectAsState(initial = false).value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            containerColor = Color.White,
            contentColor = Color.Black,

            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Settings")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black,
                        actionIconContentColor = Color.Black
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            onClick()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    }
                )
            }, content = {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 32.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(75.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(
                                text = "Enable Favorites Feature",
                                style = TextStyle(fontSize = 20.sp)
                            )
                            Switch(
                                checked = toggleFav,
                                onCheckedChange = {
                                    toggleFav = it
                                    viewModel.viewModelScope.launch{
                                        viewModel.toggleFavoritesFeature()
                                    }
                                })
                        }

                        /*Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(
                                text = "Enable Local Storage",
                                style = TextStyle(fontSize = 20.sp)
                            )
                            Switch(
                                checked = cacheEnabled,
                                onCheckedChange = {
                                    cacheEnabled = it
                                    viewModel.viewModelScope.launch{
                                        viewModel.toggleLocalStorage(cacheEnabled)
                                    }
                                })
                        }*/

                    }
                }

            })

    }

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen(){
    MyApplicationTheme {
        PetBookSettingsScreen(viewModel = PetBookSettingsViewModel(repository = object :
            PetRepository {
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

            })) {

        }
    }
}
