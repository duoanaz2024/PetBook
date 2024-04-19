package com.kodeco.android.petbook.ui.screens.settings

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class PetBookSettingsViewModel @Inject constructor(
    private val pref: PetPrefs
) : ViewModel(){


    suspend fun toggleFavoritesFeature(){
        pref.toggleFavoritesFeature()
    }

    fun getFavoritesFeatureEnabled(): Flow<Boolean> {
        return pref.getFavoritesFeatureEnabled()
    }


}