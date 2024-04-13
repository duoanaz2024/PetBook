package com.kodeco.android.petbook.dependency

import android.content.Context
import com.kodeco.android.petbook.networking.BASE_URL
import com.kodeco.android.petbook.networking.database.CountryDatabase
import com.kodeco.android.petbook.networking.RemoteApiService
import com.kodeco.android.petbook.networking.adapters.CountryAdapter
import com.kodeco.android.petbook.networking.buildClient
import com.kodeco.android.petbook.networking.dao.CountryDao
import com.kodeco.android.petbook.networking.preferences.CountryPrefs
import com.kodeco.android.petbook.networking.preferences.CountryPrefsImpl
import com.kodeco.android.petbook.repositories.CountryRepository
import com.kodeco.android.petbook.repositories.CountryRepositoryImpl
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
class CountryInfoSingletonModule {
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
    fun providesCountryDatabase(@ApplicationContext applicationContext: Context): CountryDatabase {
        return CountryDatabase.buildDatabase(applicationContext)
    }

    @Provides
    @Singleton
    fun providesCountryPrefsImpl(@ApplicationContext applicationContext: Context
    ): CountryPrefs = CountryPrefsImpl(applicationContext)

    @Provides
    @Singleton
    fun providesCountryRepository(
        service: RemoteApiService,
        database: CountryDatabase,
        prefs: CountryPrefs
    ): CountryRepository = CountryRepositoryImpl(service, database.countryDao(), prefs)


}
