package com.example.moviesearch.domain.api

import com.example.moviesearch.domain.models.Movie
import com.example.moviesearch.domain.models.MovieDetails
import util.Resource

interface MoviesRepository {
    fun searchMovies(searchQuery: String): Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun searchDetails(movieId: String): Resource<MovieDetails>
}