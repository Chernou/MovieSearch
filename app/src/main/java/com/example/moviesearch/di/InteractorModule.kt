package com.example.moviesearch.di

import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get(), get())
    }

}