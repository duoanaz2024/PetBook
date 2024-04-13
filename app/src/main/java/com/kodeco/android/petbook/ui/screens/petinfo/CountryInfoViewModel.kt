package com.kodeco.android.petbook.ui.screens.petinfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.petbook.model.Country
import com.kodeco.android.petbook.networking.preferences.CountryPrefs
import com.kodeco.android.petbook.repositories.CountryRepository
import com.kodeco.android.petbook.util.CountryInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CountryInfoViewModel @Inject constructor(
    private val repository: CountryRepository,
    val pref: CountryPrefs
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState



    init {
        refreshCountries()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun refreshCountries(){
        _uiState.value = CountryInfoState.Loading
        viewModelScope.launch {
            try{
                delay(1000)
                repository.fetchCountries()
                repository.countries
                    .catch { e ->
                        Log.d("INFO", "Exception Occurred")
                        _uiState.value = CountryInfoState.Error(e.message)

                    }
                    .collect {
                            value -> _uiState.value = CountryInfoState.Success(value)
                    }
            } catch (e: Exception) {
                Log.d("INFO", "Exception Occurred: $e")
                _uiState.value = CountryInfoState.Error(e.message)
            }


        }


    }

    fun favorite(country: Country) {
        viewModelScope.launch {
            repository.favorite(country)
        }
    }


}