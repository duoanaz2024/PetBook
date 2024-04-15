package com.kodeco.android.petbook.networking.adapters

import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.dto.PetDto
import com.kodeco.android.petbook.networking.dto.PetBreedDto
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class WrappedPetList

class PetAdapter {
    @WrappedPetList
    @FromJson
    fun fromJson(petDtoList: List<PetDto>) : List<Pet> = petDtoList.map { petDto ->
        Pet(
            id = petDto.id,
            url = petDto.url,
            width = petDto.width,
            height = petDto.height,
            breedName = petDto.breedName,
            temperament = petDto.temperament,
            origin = petDto.origin,
            description = petDto.description,
            lifeSpan = petDto.lifeSpan,
            childFriendly = petDto.childFriendly,
            energyLevel = petDto.energyLevel,
            intelligence = petDto.intelligence,
            strangerFriendly = petDto.strangerFriendly,
            wikipediaUrl = petDto.wikipediaUrl
        )
    }

    @ToJson
    fun toJson(@WrappedPetList petList: List<Pet>): List<PetDto> = petList.map { pet ->
        PetDto(
            breeds = listOf(PetBreedDto(name = pet.breedName, description = pet.description,
                origin = pet.origin, temperament = pet.temperament, lifeSpan = pet.lifeSpan,
                childFriendly = pet.childFriendly, energyLevel = pet.energyLevel,
                intelligence = pet.intelligence, strangerFriendly = pet.strangerFriendly,
                wikipediaUrl = pet.wikipediaUrl)),
            id = pet.id,
            url = pet.url,
            width = pet.width,
            height = pet.height
        )
    }
}