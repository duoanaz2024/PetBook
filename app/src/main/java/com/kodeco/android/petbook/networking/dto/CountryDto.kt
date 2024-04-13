package com.kodeco.android.petbook.networking.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    val breeds: List<CountryNameDto>?,
    val id: String,
    val url: String,
    val width: Long,
    val height: Float
) {

    var commonName = breeds?.firstOrNull()?.name ?: "N/A"
    val mainCapital = breeds?.firstOrNull()?.origin ?: "N/A"
}
