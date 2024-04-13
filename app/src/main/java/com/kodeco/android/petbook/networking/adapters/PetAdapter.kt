package com.kodeco.android.petbook.networking.adapters

import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.dto.PetDto
import com.kodeco.android.petbook.networking.dto.PetBreedDto
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class WrappedCountryList

class CountryAdapter {
    @WrappedCountryList
    @FromJson
    fun fromJson(countryDtoList: List<PetDto>) : List<Pet> = countryDtoList.map { countryDto ->
        Pet(
            commonName = countryDto.commonName,
            mainCapital = countryDto.mainCapital,
            population = countryDto.width,
            area = countryDto.height,
            flagUrl = countryDto.url,
        )
    }

    @ToJson
    fun toJson(@WrappedCountryList petList: List<Pet>): List<PetDto> = petList.map { country ->
        PetDto(
            breeds = listOf(PetBreedDto(name = country.commonName, description = country.mainCapital,
                origin = "")),
            id = "",
            url = country.flagUrl,
            width = country.population,
            height = country.area
        )
    }
}