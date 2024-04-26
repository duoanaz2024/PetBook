package com.kodeco.android.petbook.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.kodeco.android.petbook.ui.theme.MyApplicationTheme

@Composable
fun WebViewComponent(url: String) {

    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}

@Preview(showBackground = true)
@Composable
fun PreviewWebView(){
    MyApplicationTheme {
        WebViewComponent(url = "https://www.google.com")
    }
}