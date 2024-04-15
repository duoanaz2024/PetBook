package com.kodeco.android.petbook.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breedName: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val lifeSpan: String,
    val childFriendly: Int,
    val energyLevel: Int,
    val intelligence: Int,
    val strangerFriendly: Int,
    val wikipediaUrl: String,
    val isFavorite: Boolean = false
) : Parcelable
