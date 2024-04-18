package com.kodeco.android.petbook.ui.screens.favorites

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import com.kodeco.android.petbook.ui.components.FavoritesList
import com.kodeco.android.petbook.ui.components.LinearLoading
import com.kodeco.android.petbook.ui.components.PetErrorScreen
import com.kodeco.android.petbook.util.PetBookState

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
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            containerColor = Color.White,
            contentColor = Color.Black,

            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Favorites",
                            style = TextStyle(
                                fontSize = 25.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.Bold
                            ))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black,
                        actionIconContentColor = Color.Black
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

