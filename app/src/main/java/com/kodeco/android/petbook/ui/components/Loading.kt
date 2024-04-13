package com.kodeco.android.petbook.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.petbook.R
import com.kodeco.android.petbook.ui.screens.petinfo.CountryInfoViewModel
import kotlinx.coroutines.launch

@Composable
fun Loading(aboutTap: () -> Unit,
            onRefreshClick: () -> Unit) {

    val showShimmer = remember { mutableStateOf(true) }

    Column {

        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black)
            }

            Spacer(modifier = Modifier.weight((1f)))

            Button(
                onClick = { onRefreshClick() }) {
                Text("Refresh")
            }

            Spacer(modifier = Modifier.weight((1f)))

            IconButton(onClick = {aboutTap()}) {
                Icon(imageVector = Icons.Default.Info,
                    contentDescription = "About",
                    tint = Color.Black)
            }

        }

        LazyColumn {
            items(100) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = showShimmer.value
                            )
                        )
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
                            Text(text = "",
                                Modifier.padding(6.dp))

                            Text(text = "",
                                Modifier.padding(6.dp))
                        }
                    }


                }
            }
        }

    }

}

