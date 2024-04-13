package com.kodeco.android.petbook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kodeco.android.petbook.R
import com.kodeco.android.petbook.model.Country


@Composable
fun CountryInfoList(
    favoritesEnabled: State<Boolean>,
    countryList: List<Country>,
    onCountryRowTap: (Any?) -> Unit,
    onFavorite: (Country) -> Unit,
    aboutTap: () -> Unit,
    onSettingsTap: () -> Unit,
    onRefreshClick: () -> Unit,
) {

    Column {

        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = {onSettingsTap()}) {
                Icon(imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black)
            }

            Spacer(modifier = Modifier.weight((1f)))

            Button(
                onClick = onRefreshClick) {
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
            items(countryList.size) { index ->
                CountryInfoRow(
                    favoritesEnabled=favoritesEnabled,
                    country=countryList[index],
                    onFavorite=onFavorite) {
                    onCountryRowTap(index)
                }
            }
        }

    }




}
