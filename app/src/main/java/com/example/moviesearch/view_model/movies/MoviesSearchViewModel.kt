package com.example.moviesearch.view_model.movies

import com.example.moviesearch.view_model.SingleLiveEvent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.R
import com.example.moviesearch.domain.MoviesState
import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.models.Movie
import org.koin.core.component.KoinComponent
import util.ResourceProvider
import util.debounce

class MoviesSearchViewModel(
    private val moviesInteractor: MoviesInteractor,
    private val resourceProvider: ResourceProvider
) : ViewModel(), KoinComponent {

    private val stateLiveData = MutableLiveData<MoviesState>()
    private val showToast = SingleLiveEvent<String>()
    private val onSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) {searchText ->
        searchRequest(searchText)
    }

    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                is MoviesState.Content -> MoviesState.Content(movieState.movies.sortedByDescending { it.inFavorite })
                is MoviesState.Empty -> movieState
                is MoviesState.Error -> movieState
                is MoviesState.Loading -> movieState
            }
        }
    }

    private var latestSearchText: String? = null

    fun observeShowToast(): LiveData<String> = showToast

    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        this.latestSearchText = changedText
        onSearchDebounce(changedText)
    }

    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) moviesInteractor.removeMovieFromFavorites(movie)
        else moviesInteractor.addMovieToFavorites(movie)
        updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    val movies = mutableListOf<Movie>()
                    if (foundMovies != null) {
                        movies.addAll(foundMovies)
                    }

                    when {
                        errorMessage != null -> {
                            renderState(
                                MoviesState.Error(
                                    errorMessage = resourceProvider.getString(R.string.something_went_wrong),
                                )
                            )
                            showToast.postValue(errorMessage)
                        }

                        movies.isEmpty() -> {
                            renderState(
                                MoviesState.Empty(
                                    message = resourceProvider.getString(R.string.nothing_found),
                                )
                            )
                        }

                        else -> {
                            renderState(
                                MoviesState.Content(
                                    movies = movies,
                                )
                            )
                            //todo delete
                            Log.d("!@#", movies[0].title)
                            Log.d("!@#", movies[1].title)
                            Log.d("!@#", movies[2].title)
                        }
                    }
                }
            })
        }
    }

    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value
        if (currentState is MoviesState.Content) {
            val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }
            if (movieIndex != -1) {
                stateLiveData.value = MoviesState.Content(
                    currentState.movies.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}

