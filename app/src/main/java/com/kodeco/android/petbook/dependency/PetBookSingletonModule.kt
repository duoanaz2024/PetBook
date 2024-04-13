package com.kodeco.android.petbook.dependency

import android.content.Context
import com.kodeco.android.petbook.networking.BASE_URL
import com.kodeco.android.petbook.networking.database.PetDatabase
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.adapters.CountryAdapter
import com.kodeco.android.petbook.networking.buildClient
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.networking.preferences.PetPrefsImpl
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.repositories.PetRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PetBookSingletonModule {
    @Provides
    @Singleton
    fun providesCountryService(): RemoteApiService {
        val moshi = Moshi.Builder()
            .add(CountryAdapter())
            .build()

        return Retrofit.Builder()
            .client(buildClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(RemoteApiService::class.java)

    }

    @Provides
    @Singleton
    fun providesCountryDatabase(@ApplicationContext applicationContext: Context): PetDatabase {
        return PetDatabase.buildDatabase(applicationContext)
    }

    @Provides
    @Singleton
    fun providesCountryPrefsImpl(@ApplicationContext applicationContext: Context
    ): PetPrefs = PetPrefsImpl(applicationContext)

    @Provides
    @Singleton
    fun providesCountryRepository(
        service: RemoteApiService,
        database: PetDatabase,
        prefs: PetPrefs
    ): PetRepository = PetRepositoryImpl(service, database.petDao(), prefs)


}
