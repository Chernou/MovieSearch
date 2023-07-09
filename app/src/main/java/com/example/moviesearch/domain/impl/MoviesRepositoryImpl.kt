package com.example.moviesearch.domain.impl

import android.util.Log
import com.example.moviesearch.data.LocalStorage
import com.example.moviesearch.data.NetworkClient
import com.example.moviesearch.data.converters.MovieCastConverter
import com.example.moviesearch.data.dto.MovieCastRequest
import com.example.moviesearch.data.dto.MovieCastResponse
import com.example.moviesearch.data.dto.MovieDetailsRequest
import com.example.moviesearch.data.dto.MovieDetailsResponse
import com.example.moviesearch.data.dto.MoviesSearchRequest
import com.example.moviesearch.data.dto.MoviesSearchResponse
import com.example.moviesearch.data.dto.NamesRequest
import com.example.moviesearch.data.dto.NamesResponse
import com.example.moviesearch.domain.api.MoviesRepository
import com.example.moviesearch.domain.models.Movie
import com.example.moviesearch.domain.models.MovieCast
import com.example.moviesearch.domain.models.MovieDetails
import com.example.moviesearch.domain.models.Name
import org.koin.core.component.KoinComponent
import util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val castConverter: MovieCastConverter
) : MoviesRepository, KoinComponent {

    override fun searchMovies(searchQuery: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(searchQuery))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте соединение к интернету")
            }
            200 -> {
                val stored = localStorage.getSavedFavorites()
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        id = it.id,
                        resultType = it.resultType,
                        image = it.image,
                        title = it.title,
                        description = it.description,
                        inFavorite = stored.contains(it.id)
                    )
                })
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun searchDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте соединение к интернету")
            }
            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id, title, imDbRating, year,
                            countries, genres, directors, writers, stars, plot
                        )
                    )
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun searchMovieCast(movieId: String): Resource<MovieCast> {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте соединение к интернету")
            }
            200 -> {
                Resource.Success(
                    data = castConverter.convert(response as MovieCastResponse)
                )
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun searchNames(searchQuery: String) : Resource<List<Name>> {
        val response = networkClient.doRequest(NamesRequest(searchQuery))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте соединение к интернету")
            }
            200 -> {
                Log.d("!@#", (response as NamesResponse).results[0].title)
                Resource.Success((response as NamesResponse).results.map {
                    Name(
                        name = it.title,
                        description = it.description,
                        imageUrl = it.image
                    )
                })
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

}