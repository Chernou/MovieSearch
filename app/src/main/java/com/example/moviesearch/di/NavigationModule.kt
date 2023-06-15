package com.example.moviesearch.di

import com.example.moviesearch.core.navigation.Router
import com.example.moviesearch.core.navigation.RouterImpl
import org.koin.dsl.module

val navigationModule = module {
    val router = RouterImpl()

    single<Router> { router }
    single { router.navigatorHolder }
}