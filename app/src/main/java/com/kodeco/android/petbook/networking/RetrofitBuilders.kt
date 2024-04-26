package com.kodeco.android.petbook.networking

import okhttp3.OkHttpClient

fun buildClient(): OkHttpClient =
    OkHttpClient.Builder().build()
