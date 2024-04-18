package com.kodeco.android.petbook.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodeco.android.petbook.R
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar


@Composable
fun PetInfoList(
    favoritesEnabled: State<Boolean>,
    petList: List<Pet>,
    onPetRowTap: (Any?) -> Unit,
    onFavorite: (Pet) -> Unit,
    aboutTap: () -> Unit,
    onSettingsTap: () -> Unit,
    onHeartTap : () -> Unit,
    onRefreshClick: () -> Unit,
) {

    val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good Morning,"
        in 12..16 -> "Good Afternoon,"
        else -> "Good Evening,"
    }

    val config = LocalConfiguration.current
    val orientation = remember(config.orientation){config.orientation}

    Column {

        Text(
            text = greeting,
            style = TextStyle(
                fontSize = 25.sp,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(vertical = 15.dp)
        )

        Spacer(modifier = Modifier.weight((1f)))

        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)

        ){
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

                Image(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "Heart",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onHeartTap() }
                )

                Spacer(modifier = Modifier.weight((1f)))

                IconButton(onClick = {aboutTap()}) {
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = "About",
                        tint = Color.Black)
                }

            }
        }
        
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){

            Text(
                text = "Daily Feed",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .padding(vertical = 15.dp)
            )

            Spacer(modifier = Modifier.weight((1f)))

            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                tint = Color.Black,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .clickable { onRefreshClick() }
            )
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT){
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
        else{
            LazyRow(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
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




}

@Preview(showBackground = true)
@Composable
fun PreviewPetInfoList(){
    val favFlow = MutableStateFlow(true)
    val favoritesEnabled = favFlow.collectAsState()
    MyApplicationTheme {
        PetInfoList(
            favoritesEnabled = favoritesEnabled,
            petList = listOf(Pet(
                id = "1",
                url = "https://",
                width = 100,
                height = 200,
                breedName = "Cat",
                temperament = "Lovely",
                origin = "US",
                description = "Lovely Cat For Pet Book",
                lifeSpan = "5",
                childFriendly = 5,
                energyLevel = 5,
                intelligence = 6,
                strangerFriendly = 5,
                wikipediaUrl = "https://"
            )),
            onPetRowTap = { },
            onFavorite = { },
            aboutTap = { },
            onSettingsTap = { },
            onHeartTap = { }) {
            
        }
    }
}
