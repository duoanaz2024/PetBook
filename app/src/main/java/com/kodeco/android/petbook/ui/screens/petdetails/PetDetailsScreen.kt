package com.kodeco.android.petbook.ui.screens.petdetails

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

enum class MapState {
    Shrunk,
    Expanded
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetDetailsScreen(
    petId: Int,
    viewModel: PetDetailsViewModel,
    onNavigateUp: () -> Unit) {
    val country = viewModel.getPetDetails(petId)

    if (country != null){
        val capital: String = country.mainCapital

        val mapState = remember { mutableStateOf(MapState.Shrunk) }
        val transition = updateTransition(targetState = mapState, "Favorite")

        val size = transition.animateDp(label = "Size Transition") { state ->
            when(state.value){
                MapState.Shrunk -> 100.dp
                MapState.Expanded -> 200.dp
            }
        }

        val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
        val animatedColor by infiniteTransition.animateColor(
            initialValue = Color(0xFF60DDAD),
            targetValue = Color(0xFF4285F4),
            animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
            label = "color"
        )

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
                            Text(text = country.commonName)
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
                    Column {
                        Spacer(modifier = Modifier.height(75.dp))
                        Text(text = "Capital: $capital",
                            modifier = Modifier.padding(6.dp),
                            color = animatedColor)
                        Text(text = "Population: " + country.population,
                            modifier = Modifier.padding(6.dp))
                        Text(text = "Area: " + country.area,
                            Modifier.padding(6.dp))
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(country.flagUrl)
                                .crossfade(true)
                                .build(),
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = "Country Flag",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.padding(6.dp).
                            size(size.value).clickable {
                                mapState.value = when(mapState.value){
                                    MapState.Expanded -> MapState.Shrunk
                                    MapState.Shrunk -> MapState.Expanded
                                }

                            }
                        )


                    }

                })

        }

    }
    else{
        Log.d("INFO", "Country is Null")
    }


}
