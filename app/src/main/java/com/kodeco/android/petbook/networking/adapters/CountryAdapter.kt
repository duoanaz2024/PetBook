package com.kodeco.android.petbook.networking.adapters

import com.kodeco.android.petbook.model.Country
import com.kodeco.android.petbook.networking.dto.CountryDto
import com.kodeco.android.petbook.networking.dto.CountryFlagsDto
import com.kodeco.android.petbook.networking.dto.CountryNameDto
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class WrappedCountryList

class CountryAdapter {
    @WrappedCountryList
    @FromJson
    fun fromJson(countryDtoList: List<CountryDto>) : List<Country> = countryDtoList.map { countryDto ->
        Country(
            commonName = countryDto.commonName,
            mainCapital = countryDto.mainCapital,
            population = countryDto.width,
            area = countryDto.height,
            flagUrl = countryDto.url,
        )
    }

    @ToJson
    fun toJson(@WrappedCountryList countryList: List<Country>): List<CountryDto> = countryList.map { country ->
        CountryDto(
            breeds = listOf(CountryNameDto(name = country.commonName, description = country.mainCapital,
                origin = "")),
            id = "",
            url = country.flagUrl,
            width = country.population,
            height = country.area
        )
    }
}