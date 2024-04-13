package com.kodeco.android.petbook.networking.dto
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class CountryNameDto(val name: String, val origin: String, val description: String)
