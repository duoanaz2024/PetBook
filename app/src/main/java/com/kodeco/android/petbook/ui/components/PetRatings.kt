package com.kodeco.android.petbook.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.petbook.R
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme

@Composable
fun PetRating(
    measurement: String,
    rating: Int
){
    Column{
        Text(text = "$measurement: ",
            Modifier.padding(6.dp))
        Row{
            for (i in 1..rating) {
                Icon(
                    painter = painterResource(id = R.drawable.star_filled),
                    contentDescription = "Rating",
                    tint = Color.Magenta,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(2.dp)
                )
            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewPetRating(){
    MyApplicationTheme {
        PetRating(measurement = "ChildFriendly", rating = 4)
    }
}