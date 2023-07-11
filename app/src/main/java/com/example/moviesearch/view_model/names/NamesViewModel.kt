package com.example.moviesearch.view_model.names

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.domain.NamesState
import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.models.Name
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NamesViewModel(
    private val interactor: MoviesInteractor,
    ) : ViewModel() {

    private val namesState = MutableLiveData<NamesState>()
    private var latestSearchText: String? = null
    private var searchJob: Job? = null

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
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchNames(changedText)
        }
    }

    private fun renderState(state: NamesState) {
        namesState.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}