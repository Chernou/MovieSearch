package com.example.moviesearch.domain.impl

import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.api.MoviesRepository
import com.example.moviesearch.domain.models.Movie
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.Resource
import java.util.concurrent.ExecutorService

class MoviesInteractorImpl : MoviesInteractor, KoinComponent {

    private val repository: MoviesRepository by inject()
    private val executor: ExecutorService by inject()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when(val resource = repository.searchMovies(expression)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}
