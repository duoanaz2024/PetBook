package com.kodeco.android.petbook.networking.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PetBreedDto(
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @Json(name = "life_span")
    val lifeSpan: String,
    @Json(name = "child_friendly")
    val childFriendly: Int,
    @Json(name = "energy_level")
    val energyLevel: Int,
    val intelligence: Int,
    @Json(name = "stranger_friendly")
    val strangerFriendly: Int,
    @Json(name = "wikipedia_url")
    val wikipediaUrl: String?
)
