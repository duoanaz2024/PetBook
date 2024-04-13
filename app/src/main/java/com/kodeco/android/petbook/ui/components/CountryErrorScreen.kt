package com.kodeco.android.petbook.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CountryErrorScreen(headline: String, subtitle: String, onClick: () -> Unit) {
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
            Icon(
                modifier = Modifier.graphicsLayer(scaleY = 2f, scaleX = 2f),
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                tint = Color(android.graphics.Color.parseColor("#FF5252"))
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = headline
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = subtitle
            )
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .align(alignment = Alignment.BottomCenter),
            onClick = {
                onClick()
            }) {
            Text("Refresh")
        }
    }
}

