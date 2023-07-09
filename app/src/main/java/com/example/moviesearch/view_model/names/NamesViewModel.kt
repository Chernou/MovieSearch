package com.example.moviesearch.view_model.names

import android.os.Handler
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.domain.NamesState
import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.models.Name
import kotlinx.coroutines.Job

class NamesViewModel(
    private val interactor: MoviesInteractor,
    private val handler: Handler
    ) : ViewModel() {

    private val namesState = MutableLiveData<NamesState>()
    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun observeNamesState(): LiveData<NamesState> = namesState

    fun onTextChanged(changedText: String?) {
        if (!changedText.isNullOrEmpty()) {
            searchDebounce(changedText)
        }
    }

    private fun searchNames(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            renderState(NamesState.Loading)
            interactor.searchNames(searchQuery, object : MoviesInteractor.NamesConsumer {
                override fun consume(foundNames: List<Name>?, errorMessage: String?) {
                    if (foundNames != null) {
                        renderState(NamesState.Content(foundNames))
                        Log.d("!@#", foundNames[0].name)
                    } else if (errorMessage != null) {
                        renderState(NamesState.Error(errorMessage))
                    }
                }
            })
        }
    }

    private fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable { searchNames(changedText) }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(searchRunnable, SEARCH_REQUEST_TOKEN, postTime)
    }

    private fun renderState(state: NamesState) {
        namesState.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }
}