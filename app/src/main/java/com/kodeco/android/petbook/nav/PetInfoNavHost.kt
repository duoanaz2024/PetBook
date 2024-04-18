package com.kodeco.android.petbook.nav

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.petbook.ui.screens.about.PetBookAboutScreen
import com.kodeco.android.petbook.ui.screens.favorites.FavoritesScreen
import com.kodeco.android.petbook.ui.screens.petdetails.PetDetailsScreen
import com.kodeco.android.petbook.ui.screens.petinfo.PetInfoScreen
import com.kodeco.android.petbook.ui.screens.settings.PetBookSettingsScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PetInfoNavHost(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "petList"){


        composable("petList"){
            PetInfoScreen(
                viewModel = hiltViewModel(),
                onPetRowTap = { petIndex ->
                    navController.navigate("details/feed/$petIndex")
                },
                onSettingsTap = {
                    navController.navigate("settingsScreen")
                },
                onHeartTap = {
                    navController.navigate("petFavorites")
                }
            ){
                navController.navigate("aboutScreen")
            }
        }

        composable("petFavorites"){
            FavoritesScreen(
                viewModel = hiltViewModel(),
                onPetRowTap = { petIndex ->
                    navController.navigate("details/favorites/$petIndex")
                }
            ){
                navController.navigateUp()
            }
        }

        composable("details/{type}/{petIndex}",
            arguments = listOf(navArgument("petIndex") { type = NavType.IntType},
                navArgument("type") { type = NavType.StringType})){
                backStackEntry ->
            val petIndex = backStackEntry.arguments?.getInt("petIndex") ?: 0
            val type = backStackEntry.arguments?.getString("type") ?: "feed"

            PetDetailsScreen(
                petId = petIndex,
                type=type,
                viewModel = hiltViewModel()
            ) {
                navController.navigateUp()
            }

        }

        composable("aboutScreen"){
            PetBookAboutScreen(
            ){
                navController.navigateUp()
            }
        }

        composable("settingsScreen"){
            PetBookSettingsScreen(
                viewModel = hiltViewModel()
            ){
                navController.navigateUp()
            }
        }

    }

}