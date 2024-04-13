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
import com.kodeco.android.petbook.ui.screens.petdetails.PetDetailsScreen
import com.kodeco.android.petbook.ui.screens.petinfo.PetInfoScreen
import com.kodeco.android.petbook.ui.screens.settings.PetBookSettingsScreen
import com.kodeco.android.petbook.ui.screens.splash.SplashScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PetInfoNavHost(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "petList"){

        composable(route = "splash") {
            SplashScreen(
                onSplashEndedValid = {
                    navController.navigate("petList")
                }
            )
        }

        composable("petList"){
            PetInfoScreen(
                viewModel = hiltViewModel(),
                onPetRowTap = { petIndex ->
                    navController.navigate("details/$petIndex")
                },
                onSettingsTap = {
                    navController.navigate("settingsScreen")
                }
            ){
                navController.navigate("aboutScreen")
            }
        }

        composable("details/{petIndex}",
            arguments = listOf(navArgument("petIndex") { type = NavType.IntType})){
                backStackEntry ->
            val petIndex = backStackEntry.arguments?.getInt("petIndex") ?: 0

            PetDetailsScreen(
                petId = petIndex,
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