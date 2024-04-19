package com.kodeco.android.petbook.dependency

import android.content.Context
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.dao.PetDao
import com.kodeco.android.petbook.networking.database.PetDatabase
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class HiltDependencyTest {

    @Test
    fun providesPetServiceTest(){
        val singletonModule1 = PetBookSingletonModule()
        val singletonModule2 = PetBookSingletonModule()
        Assert.assertNotEquals(singletonModule1.providesPetService(),
            singletonModule2.providesPetService())
    }

    @Test
    fun providesPetDatabaseTest(){
        val singletonModule1 = PetBookSingletonModule()
        val singletonModule2 = PetBookSingletonModule()
        val mockContext = mock<Context>()
        Assert.assertNotEquals(singletonModule1.providesPetDatabase(mockContext),
            singletonModule2.providesPetDatabase(mockContext))
    }

    @Test
    fun providesPetRepositoryTest(){
        val singletonModule1 = PetBookSingletonModule()
        val singletonModule2 = PetBookSingletonModule()
        val mockDao = mock<PetDatabase>()
        val mockApiService = mock<RemoteApiService>()
        val prefs = mock<PetPrefs>()
        whenever(mockDao.petDao()).thenReturn(mock<PetDao>())
        Assert.assertNotEquals(
            singletonModule1.providesPetRepository(mockApiService, mockDao, prefs),
            singletonModule2.providesPetRepository(mockApiService, mockDao, prefs))
    }


}