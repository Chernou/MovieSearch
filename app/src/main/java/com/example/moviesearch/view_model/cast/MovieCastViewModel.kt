package com.example.moviesearch.view_model.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.domain.CastState
import com.example.moviesearch.domain.api.MoviesInteractor

class MovieCastViewModel(movieId: String, moviesInteractor: MoviesInteractor) : ViewModel() {

    init {
        moviesInteractor.searchCast(movieId, object : MoviesInteractor.CastConsumer {
            override fun consume(foundMovieCast: com.example.moviesearch.data.dto.MovieCastResponse?, errorMessage: String?) {
                if (foundMovieCast != null) {
                    stateLiveData.postValue(CastState.ContentState(foundMovieCast))
                } else if (errorMessage != null) {
                    stateLiveData.postValue(CastState.ErrorState(errorMessage))
                }
            }
        })
    }

    private val stateLiveData = MutableLiveData<CastState>()
    fun observeCast(): LiveData<CastState> = stateLiveData

}