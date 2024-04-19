package com.kodeco.android.petbook.networking.dao
/*

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.database.PetDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DaoTest {

    private val petList = listOf(
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
    )

    private lateinit var database: PetDatabase
    private lateinit var petDao: PetDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), PetDatabase::class.java).build()
        petDao = database.petDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun getPetsTest() = runTest {

        petDao.insertPets(petList)
        val result = petDao.getPets()
        Assert.assertEquals(result[0].id, petList[0].id)


    }

}
*/
