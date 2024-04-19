package com.kodeco.android.petbook.ui.screens.petinfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.networking.preferences.PetPrefs
import com.kodeco.android.petbook.repositories.PetRepository
import com.kodeco.android.petbook.util.PetBookState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class PetInfoViewModel @Inject constructor(
    private val repository: PetRepository,
    val pref: PetPrefs
) : ViewModel() {

    private val _uiState = MutableStateFlow<PetBookState>(PetBookState.Loading)
    val uiState: StateFlow<PetBookState> = _uiState



    init {
        refreshPets()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun refreshPets(){
        _uiState.value = PetBookState.Loading
        viewModelScope.launch {
            try{
                //delay(1000)
                repository.fetchPets()
                repository.pets
                    .catch { e ->
                        _uiState.value = PetBookState.Error(e.message)

                    }
                    .collect {
                            value -> _uiState.value = PetBookState.Success(value)
                    }
            } catch (e: Exception) {
                _uiState.value = PetBookState.Error(e.message)
            }


        }


    }

    fun favorite(pet: Pet) {
        viewModelScope.launch {
            repository.favorite(pet)
        }
    }


}