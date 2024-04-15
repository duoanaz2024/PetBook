package com.kodeco.android.petbook.ui.components

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodeco.android.petbook.model.Pet


@Composable
fun PetInfoList(
    favoritesEnabled: State<Boolean>,
    petList: List<Pet>,
    onPetRowTap: (Any?) -> Unit,
    onFavorite: (Pet) -> Unit,
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
                    tint = Color.Gray)
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
                    tint = Color.Gray)
            }

        }
        Text(
            text = "Daily Feed",
            style = TextStyle(
                fontSize = 25.sp,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 6.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(petList.size) { index ->
                PetInfoRow(
                    favoritesEnabled=favoritesEnabled,
                    pet=petList[index],
                    onFavorite=onFavorite) {
                    onPetRowTap(index)
                }
            }
        }

    }




}
