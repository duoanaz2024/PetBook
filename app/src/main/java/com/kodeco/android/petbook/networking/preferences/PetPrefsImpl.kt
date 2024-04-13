package com.kodeco.android.petbook.networking.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "pet_prefs"

class PetPrefsImpl (private val context: Context)  : PetPrefs {
    private val Context.dataStore by preferencesDataStore(name = STORE_NAME)
    private val dataStore = context.dataStore
    override fun getLocalStorageEnabled(): Flow<Boolean> {
        val keyName = booleanPreferencesKey("cacheEnabled")
        return dataStore.data.map {
                settings -> settings[keyName] ?: true
        }
    }

    override fun getFavoritesFeatureEnabled(): Flow<Boolean> {
        val keyName = booleanPreferencesKey("favoritesEnabled")
        return dataStore.data.map {
                settings -> settings[keyName] ?: true
        }
    }

    override suspend fun toggleLocalStorage() {
        val keyName = booleanPreferencesKey("cacheEnabled")
        val favoritesEnabled = dataStore.data.first()[keyName] ?: true
        val bool = !favoritesEnabled
        dataStore.edit { settings -> settings[keyName] = bool }
    }

    override suspend fun toggleFavoritesFeature() {
        val keyName = booleanPreferencesKey("favoritesEnabled")
        val favoritesEnabled = dataStore.data.first()[keyName] ?: true
        val bool = !favoritesEnabled
        dataStore.edit { settings -> settings[keyName] = bool }
    }
}