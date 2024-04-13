package com.kodeco.android.petbook.util

import com.kodeco.android.petbook.model.Pet

sealed class PetBookState{
    class Success(val countries: List<Pet>):PetBookState(){
    }
    class Error(val message: String?) : PetBookState(){
    }

    data object Loading : PetBookState()
}