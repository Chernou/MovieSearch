package com.example.moviesearch.view_model.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.domain.CastState
import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.models.MovieCast
import com.example.moviesearch.ui.cast.MoviesCastRVItem

class MovieCastViewModel(movieId: String, moviesInteractor: MoviesInteractor) : ViewModel() {

    private val stateLiveData = MutableLiveData<CastState>()
    fun observeCast(): LiveData<CastState> = stateLiveData

    init {

        moviesInteractor.searchCast(movieId, object : MoviesInteractor.CastConsumer {
            override fun consume(foundMovieCast: MovieCast?, errorMessage: String?) {
                if (foundMovieCast != null) {
                    stateLiveData.postValue(castToUiStateContent(foundMovieCast))
                } else {
                    stateLiveData.postValue(CastState.ErrorState(errorMessage ?: "Unknown error"))
                }
            }
        })
    }


    private fun castToUiStateContent(cast: MovieCast): CastState {
        val items = buildList<MoviesCastRVItem> {
            if (cast.directors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MoviesCastRVItem.PersonItem(it) }
            }

            if (cast.writers.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MoviesCastRVItem.PersonItem(it) }
            }

            if (cast.actors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MoviesCastRVItem.PersonItem(it) }
            }

            if (cast.others.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Others")
                this += cast.others.map { MoviesCastRVItem.PersonItem(it) }
            }
        }

        return CastState.ContentState(
            fullTitle = cast.fullTitle,
            items = items
        )
    }

}