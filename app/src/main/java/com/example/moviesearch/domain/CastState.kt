package com.example.moviesearch.domain

import com.example.moviesearch.data.dto.MovieCastResponse

sealed interface CastState {
    data class ContentState(val castState: MovieCastResponse) : CastState
    data class ErrorState(val errorMessage: String) : CastState
}