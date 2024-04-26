package com.kodeco.android.petbook.networking.adapters
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.dto.PetBreedDto
import com.kodeco.android.petbook.networking.dto.PetDto
import org.junit.Test
import org.junit.Assert.*


class PetAdapterTest {

    @Test
    fun toJsonTest() {
        val petAdapter = PetAdapter()
        val petDtoList = petAdapter.toJson(listOf(
            Pet(
            id = "1",
            url = "https://",
            width = 100,
            height = 200,
            breedName = "Cat",
            temperament = "Lovely",
            origin = "US",
            description = "Lovely Cat For Pet Book",
            lifeSpan = "5",
            childFriendly = 5,
            energyLevel = 5,
            intelligence = 6,
            strangerFriendly = 5,
            wikipediaUrl = "https://"
        )
        ))
        assertEquals(petDtoList[0].id, "1")
    }

    @Test
    fun fromJsonTest() {
        val petAdapter = PetAdapter()
        val petList = petAdapter.fromJson(listOf(
            PetDto(
                breeds = listOf(
                    PetBreedDto(name = "Cat", description = "Lovely Cat For Pet Book",
                    origin = "US", temperament = "Lovely", lifeSpan = "5",
                    childFriendly = 5, energyLevel = 5,
                    intelligence = 5, strangerFriendly = 5,
                    wikipediaUrl = "https://")
                ),
                id = "1",
                url = "https://",
                width = 100,
                height = 200
            )
        ))
        assertEquals(petList[0].id, "1")
    }
}