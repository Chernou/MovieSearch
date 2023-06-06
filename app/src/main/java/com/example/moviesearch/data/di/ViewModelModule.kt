package com.example.moviesearch.data.di

import com.example.moviesearch.presentation.movies.MoviesSearchViewModel
import com.example.moviesearch.presentation.poster.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { (url: String) ->
        PosterViewModel(url)
    }

    viewModel {
        MoviesSearchViewModel()
    }
}