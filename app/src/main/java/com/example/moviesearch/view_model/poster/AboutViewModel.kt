package com.example.moviesearch.view_model.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.domain.AboutState
import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.models.MovieDetails

class AboutViewModel(
    movieId: String,
    moviesInteractor: MoviesInteractor
) : ViewModel() {

    init {
        moviesInteractor.searchDetails(movieId, object : MoviesInteractor.DetailsConsumer {
            override fun consume(foundMovieDetails: MovieDetails?, errorMessage: String?) {
                if (foundMovieDetails != null) {
                    stateLiveData.postValue(AboutState.ContentState(foundMovieDetails))
                } else if (errorMessage != null) {
                    stateLiveData.postValue(AboutState.ErrorState(errorMessage))
                }
            }
        })
    }

    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeAboutState(): LiveData<AboutState> = stateLiveData
}