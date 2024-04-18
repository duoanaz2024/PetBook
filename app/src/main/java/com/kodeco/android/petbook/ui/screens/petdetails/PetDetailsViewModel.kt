package com.kodeco.android.petbook.ui.screens.petdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.petbook.model.Pet
import com.kodeco.android.petbook.repositories.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDetailsViewModel @Inject constructor(
    private val repository: PetRepository
) : ViewModel() {
    fun getPetDetails(petId: Int, type: String): Pet? {
        return repository.getPet(petId, type)
    }

}


