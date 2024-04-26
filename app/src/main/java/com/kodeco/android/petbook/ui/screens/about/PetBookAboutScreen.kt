package com.kodeco.android.petbook.ui.screens.about

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetBookAboutScreen(onClick: () -> Unit) {

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
                        Text(text = "About",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.Bold))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black,
                        actionIconContentColor = Color.Black
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            onClick()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    }
                )
            }, content = {
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
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "PetBook",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.Bold)
                        )
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "An App for pet lovers to get a daily" +
                                    " feed of Pet Information. Initial Version of" +
                                    " this app supports feed for Cats",
                            style = TextStyle(
                                    fontSize = 20.sp,
                            color = Color.Black,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold
                        ),
                        )
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Version:${com.kodeco.android.petbook.BuildConfig.VERSION_NAME}",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.Bold)
                        )
                    }
                }

            })

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen(){
    MyApplicationTheme {
        PetBookAboutScreen {
        }
    }
}


