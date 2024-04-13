package com.kodeco.android.petbook.networking.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.dao.PetDao

@Database(entities = [Pet::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao

    companion object {
        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            PetDatabase::class.java,
            "country-db"
        ).build()
    }
}