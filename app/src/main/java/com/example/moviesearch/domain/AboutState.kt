package com.example.moviesearch.domain

import com.example.moviesearch.domain.models.MovieDetails

sealed interface AboutState {
    data class ContentState(val movieDetails: MovieDetails) : AboutState
    data class ErrorState(val errorMessage: String) : AboutState
}