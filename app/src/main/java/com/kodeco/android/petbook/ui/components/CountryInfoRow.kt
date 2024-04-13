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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kodeco.android.petbook.model.Country
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kodeco.android.petbook.R
import androidx.compose.material3.Icon
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun CountryInfoRow(
    favoritesEnabled: State<Boolean>,
    country: Country,
    onFavorite: (Country) ->Unit,
    updateCountryDetails: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val capital: String = country.mainCapital
    val isFavorite = country.isFavorite
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

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        modifier = Modifier
            .clickable {
                updateCountryDetails()
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
                Text(
                    text = "Name: " + country.commonName,
                    Modifier.padding(6.dp)
                )

                Text(
                    text = "Capital: $capital",
                    Modifier.padding(6.dp)
                )
            }
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
                            onFavorite(country)
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



        }


    }

}

