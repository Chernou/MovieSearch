package com.example.moviesearch.di

import com.example.moviesearch.domain.api.MoviesRepository
import com.example.moviesearch.domain.impl.MoviesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }

}