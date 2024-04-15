package com.kodeco.android.petbook

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.kodeco.android.petbook.nav.PetInfoNavHost
import com.kodeco.android.petbook.networking.NetworkStatusChecker
import com.kodeco.android.petbook.ui.components.PetErrorScreen
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : MainActivityViewModel by viewModels()

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(getSystemService(ConnectivityManager::class.java))
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
 
        setContent {
            MyApplicationTheme {

                if (networkStatusChecker.hasInternetConnection()){
                    PetInfoNavHost()

                }
                else{
                    PetErrorScreen(
                        headline = "No Internet Connection",
                        subtitle = "Please Connect To Network"
                    ){
                        val intent = Intent(this, MainActivity::class.java)
                        finish()
                        startActivity(intent);
                    }
                    }

                }
            }
        }
}
