package com.example.moviesearch.domain

import com.example.moviesearch.core.ui.RVItem

sealed interface CastState {
    data class ContentState(
        val fullTitle: String,
        val items: List<RVItem>,
    ) : CastState

    data class ErrorState(val errorMessage: String) : CastState
}