package com.kodeco.android.petbook.ui.screens.settings

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.petbook.model.Country
import com.kodeco.android.petbook.networking.database.CountryDatabase
import com.kodeco.android.petbook.networking.preferences.CountryPrefs
import com.kodeco.android.petbook.repositories.CountryRepository
import com.kodeco.android.petbook.util.CountryInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CountryAppSettingsViewModel @Inject constructor(
    private val pref: CountryPrefs,
    private val database: CountryDatabase,
    private val repository: CountryRepository
) : ViewModel(){

    suspend fun toggleLocalStorage(checked: Boolean){
        pref.toggleLocalStorage()
        if (!checked){
            database.countryDao().deleteAllCountries()
        }
        try{
            repository.fetchCountries()
        } catch (e: Exception) {
            Log.d("INFO", "Exception Occurred: $e")
        }


    }

    suspend fun toggleFavoritesFeature(){
        pref.toggleFavoritesFeature()
    }

    fun getFavoritesFeatureEnabled(): Flow<Boolean> {
        return pref.getFavoritesFeatureEnabled()
    }

    fun getLocalStorageEnabled(): Flow<Boolean> {
        return pref.getLocalStorageEnabled()
    }

}