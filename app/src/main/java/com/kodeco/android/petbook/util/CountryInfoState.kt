package com.kodeco.android.petbook.util

import android.os.Parcelable
import com.kodeco.android.petbook.model.Country
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

sealed class CountryInfoState{
    class Success(val countries: List<Country>):CountryInfoState(){
    }
    class Error(val message: String?) : CountryInfoState(){
    }

    data object Loading : CountryInfoState()
}