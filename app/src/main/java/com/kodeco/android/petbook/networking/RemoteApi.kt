package com.kodeco.android.petbook.networking


const val BASE_URL = "https://api.thecatapi.com/"

class RemoteApi(private val remoteApiService: RemoteApiService) {

   /* fun getCountries(){

        remoteApiService.getCountries().enqueue(object : Callback<List<Country>> {

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                val countryName = response.body()
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


    }*/


}