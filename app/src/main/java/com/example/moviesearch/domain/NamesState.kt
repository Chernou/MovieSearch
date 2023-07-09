package com.example.moviesearch.domain

import com.example.moviesearch.domain.models.Name

sealed interface NamesState {

    object Loading : NamesState

    data class Content(
        val names: List<Name>
    ) : NamesState

    data class Error(
        val errorMessage: String
    ) : NamesState

    data class Empty(
        val message: String
    ) : NamesState

}