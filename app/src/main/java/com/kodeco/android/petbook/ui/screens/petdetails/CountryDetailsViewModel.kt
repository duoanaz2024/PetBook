package com.kodeco.android.petbook.ui.screens.petdetails

import androidx.lifecycle.ViewModel
import com.kodeco.android.petbook.model.Country
import com.kodeco.android.petbook.repositories.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {
    fun getCountryDetails(countryId: Int): Country? {
        return repository.getCountry(countryId)
    }

}


