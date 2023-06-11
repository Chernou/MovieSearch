package com.example.moviesearch.domain.impl

import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.api.MoviesRepository
import com.example.moviesearch.domain.models.Movie
import org.koin.core.component.KoinComponent
import util.Resource
import java.util.concurrent.ExecutorService

class MoviesInteractorImpl(
    private val repository: MoviesRepository,
    private val executor: ExecutorService
) : MoviesInteractor, KoinComponent {

    override fun searchMovies(searchQuery: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when (val resource = repository.searchMovies(searchQuery)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }

    override fun searchDetails(movieId: String, consumer: MoviesInteractor.DetailsConsumer) {
        executor.execute {
            when (val resource = repository.searchDetails(movieId)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }

    override fun searchCast(movieId: String, consumer: MoviesInteractor.CastConsumer) {
        executor.execute {
            when (val resource = repository.searchMovieCast(movieId)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
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
