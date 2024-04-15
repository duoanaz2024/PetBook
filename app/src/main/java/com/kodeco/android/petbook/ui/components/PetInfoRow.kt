package com.kodeco.android.petbook.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kodeco.android.petbook.model.Pet
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kodeco.android.petbook.R
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.kodeco.android.petbook.ui.screens.petdetails.MapState
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun PetInfoRow(
    favoritesEnabled: State<Boolean>,
    pet: Pet,
    onFavorite: (Pet) ->Unit,
    updatePetDetails: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val dimension: String = pet.height.toString() + " x " + pet.width.toString()
    val isFavorite = pet.isFavorite
    val drawableId = if (isFavorite){R.drawable.star_filled}else{
        R.drawable.star_outline
    }
    val targetRotation = if (isFavorite){0f}else{
        360f
    }

    val transition = updateTransition(targetState = isFavorite, "Favorite")

    val color = transition.animateColor(label = "Color Transition") { fav ->
        when(fav){
            true -> Color.Yellow
            false -> Color.Black
        }
    }



    val rotateAnimation = remember { Animatable(0f) }
    val sizeAnimation = remember { Animatable(40f) }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .clickable {
                updatePetDetails()
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)

    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){

            Column {
                if (favoritesEnabled.value) {
                    Icon(
                        painter = painterResource(id = drawableId),
                        contentDescription = "Favorite",
                        tint = color.value,
                        modifier = Modifier
                            .rotate(rotateAnimation.value)
                            .size(sizeAnimation.value.dp)
                            .padding(6.dp)
                            .clickable {
                                onFavorite(pet)
                                coroutineScope.launch {
                                    rotateAnimation.animateTo(
                                        targetValue = targetRotation,
                                        animationSpec = tween(durationMillis = 2000)
                                    )
                                    sizeAnimation.animateTo(
                                        targetValue = 50f,
                                        animationSpec = tween(durationMillis = 100)
                                    )
                                    sizeAnimation.animateTo(
                                        targetValue = 40f,
                                        animationSpec = tween(durationMillis = 100)
                                    )

                                }
                            },
                    )
                }
                Text(
                    text = "Name: " + pet.breedName,
                    modifier = Modifier.padding(6.dp)
                )

                Text(
                    text = "Size: $dimension",
                    style = TextStyle(
                        color = Color.Gray,
                    ),
                    modifier = Modifier.padding(6.dp)
                )
                Row{
                    Icon(
                        painter = painterResource(id = R.drawable.star_filled),
                        contentDescription = "Rating",
                        tint = Color.Magenta,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(6.dp)
                    )
                    Text(
                        text = pet.energyLevel.toString(),
                        modifier = Modifier.padding(6.dp)
                    )
                }


            }

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pet.url)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator(modifier = Modifier.size(10.dp), color = Color.Gray)
                },
                contentDescription = "Pet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
            )





        }


    }

}

