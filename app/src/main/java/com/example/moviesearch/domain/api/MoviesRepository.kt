package com.example.moviesearch.domain.api

import com.example.moviesearch.domain.models.Movie
import util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}