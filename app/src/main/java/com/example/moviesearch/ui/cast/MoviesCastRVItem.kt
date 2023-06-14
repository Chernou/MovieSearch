package com.example.moviesearch.ui.cast

import com.example.moviesearch.core.ui.RVItem
import com.example.moviesearch.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem : RVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}