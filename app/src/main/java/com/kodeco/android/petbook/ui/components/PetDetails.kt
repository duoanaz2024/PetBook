package com.kodeco.android.petbook.ui.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.ui.screens.petdetails.MapState

@Composable
fun PetDetails(pet: Pet){

    val mapState = remember { mutableStateOf(MapState.Shrunk) }
    val transition = updateTransition(targetState = mapState, "Favorite")

    val size = transition.animateDp(label = "Size Transition") { state ->
        when(state.value){
            MapState.Shrunk -> 150.dp
            MapState.Expanded -> 300.dp
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF60DDAD),
        targetValue = Color(0xFF4285F4),
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
        Spacer(modifier = Modifier.height(75.dp))
        Text(text = "Temperament: ${pet.temperament}",
            modifier = Modifier.padding(6.dp),
            color = animatedColor)
        Text(text = "Origin: " + pet.origin,
            modifier = Modifier.padding(6.dp))
        Text(text = "Description: " + pet.description,
            Modifier.padding(6.dp))
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pet.url)
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator()
            },
            contentDescription = "Pet Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(6.dp)
                .size(size.value)
                .clickable {
                    mapState.value = when (mapState.value) {
                        MapState.Expanded -> MapState.Shrunk
                        MapState.Shrunk -> MapState.Expanded
                    }

                }
        )
        Text(text = "Life Span: " + pet.lifeSpan,
            Modifier.padding(6.dp))
        PetRating("Child Friendly", pet.childFriendly)
        PetRating("Energy Level", pet.energyLevel)
        PetRating("Intelligence", pet.intelligence)
        PetRating("Stranger Friendly", pet.strangerFriendly)
        Text(text = "Wikipedia: " + pet.wikipediaUrl.toString(),
            Modifier.padding(6.dp))


    }
}