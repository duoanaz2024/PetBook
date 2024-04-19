package com.kodeco.android.petbook.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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


@Composable
fun FavoritesList(
    petList: List<Pet>,
    favoritesEnabled: State<Boolean>,
    onFavorite: (Pet) -> Unit,
    onPetRowTap: (Any?) -> Unit,
) {

    val config = LocalConfiguration.current
    val orientation = remember(config.orientation){config.orientation}

    Column {

        Spacer(modifier = Modifier.height(50.dp))

        if (!favoritesEnabled.value){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cat_svgrepo_com_2),
                        contentDescription = "Cat"
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Please Enable Favorites Feature In Settings",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold)
                    )
                }
            }
        }else{
            if (petList.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 32.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cat_svgrepo_com_2),
                            contentDescription = "Cat"
                        )
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "No Favorites",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
            else{
                if (orientation == Configuration.ORIENTATION_PORTRAIT){
                    LazyColumn(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(petList.size) { index ->
                            FavoritesRow(
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
                            FavoritesRow(
                                pet=petList[index],
                                onFavorite=onFavorite) {
                                onPetRowTap(index)
                            }
                        }
                    }
                }

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewFavoritesList(){
    val favFlow = MutableStateFlow(true)
    val favoritesEnabled = favFlow.collectAsState()
    val petList = listOf(Pet(
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
    ))
    MyApplicationTheme {
        FavoritesList(petList = petList,
            favoritesEnabled = favoritesEnabled,
            onFavorite = {},
            onPetRowTap = {})
    }
}
