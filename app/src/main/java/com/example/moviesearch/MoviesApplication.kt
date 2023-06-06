package com.example.moviesearch

import android.app.Application
import com.example.moviesearch.data.di.dataModule
import com.example.moviesearch.data.di.interactorModule
import com.example.moviesearch.data.di.repositoryModule
import com.example.moviesearch.data.di.threadsModule
import com.example.moviesearch.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication() : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(
                dataModule,
                interactorModule,
                repositoryModule,
                threadsModule,
                viewModelModule
            )
        }
    }
}