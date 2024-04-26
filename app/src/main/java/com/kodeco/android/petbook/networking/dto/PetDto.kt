package com.kodeco.android.petbook.networking.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PetDto(
    val breeds: List<PetBreedDto>?,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
) {
    var breedName = breeds?.firstOrNull()?.name ?: "N/A"
    val temperament = breeds?.firstOrNull()?.temperament ?: "N/A"
    var origin = breeds?.firstOrNull()?.origin ?: "N/A"
    val description = breeds?.firstOrNull()?.description ?: "N/A"
    var lifeSpan = breeds?.firstOrNull()?.lifeSpan ?: "N/A"
    val childFriendly = breeds?.firstOrNull()?.childFriendly ?: 0
    var energyLevel = breeds?.firstOrNull()?.energyLevel ?: 0
    val intelligence = breeds?.firstOrNull()?.intelligence ?: 0
    var strangerFriendly = breeds?.firstOrNull()?.strangerFriendly ?: 0
    var wikipediaUrl = breeds?.firstOrNull()?.wikipediaUrl ?: "N/A"
}
