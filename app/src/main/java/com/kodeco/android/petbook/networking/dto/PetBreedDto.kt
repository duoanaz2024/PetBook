package com.kodeco.android.petbook.networking.dto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PetBreedDto(val name: String, val origin: String, val description: String)
