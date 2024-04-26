package com.kodeco.android.petbook.networking.preferences

import kotlinx.coroutines.flow.Flow

interface PetPrefs {
    fun getLocalStorageEnabled(): Flow<Boolean>
    fun getFavoritesFeatureEnabled(): Flow<Boolean>

    suspend fun toggleLocalStorage()
    suspend fun toggleFavoritesFeature()
}