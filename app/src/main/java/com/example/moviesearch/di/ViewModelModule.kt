package com.example.moviesearch.di

import com.example.moviesearch.view_model.cast.MovieCastViewModel
import com.example.moviesearch.view_model.movies.MoviesSearchViewModel
import com.example.moviesearch.view_model.names.NamesViewModel
import com.example.moviesearch.view_model.poster.AboutViewModel
import com.example.moviesearch.view_model.poster.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { (url: String) ->
        PosterViewModel(url)
    }

    viewModel {
        MoviesSearchViewModel(get(), get(), get())
    }

    viewModel {
        AboutViewModel(get())
    }

    viewModel {
        MovieCastViewModel(get())
    }

    viewModel {
        NamesViewModel(get(), get())
    }
}