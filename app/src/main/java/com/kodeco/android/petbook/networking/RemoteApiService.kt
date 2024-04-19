package com.kodeco.android.petbook.networking

import com.kodeco.android.petbook.BuildConfig
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.adapters.WrappedPetList
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApiService {

    @GET("v1/images/search?size=small&limit=50&api_key=${BuildConfig.apikey}&has_breeds=1")
    @WrappedPetList
    suspend fun getPets(): Response<List<Pet>>

}