package com.example.moviesearch.domain.api

import com.example.moviesearch.domain.models.Movie
import com.example.moviesearch.domain.models.MovieCast
import com.example.moviesearch.domain.models.MovieDetails

interface MoviesInteractor {
    fun searchMovies(searchQuery: String, consumer: MoviesConsumer)
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun searchDetails(movieId: String, consumer: DetailsConsumer)
    fun searchCast(movieId: String, consumer: CastConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    interface DetailsConsumer {
        fun consume(foundMovieDetails: MovieDetails?, errorMessage: String?)
    }

    interface CastConsumer {
        fun consume(foundMovieCast: MovieCast?, errorMessage: String?)
    }
}