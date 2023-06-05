package com.example.moviesearch

import android.app.Application
import com.example.moviesearch.presentation.movies.MoviesSearchViewModel

class MoviesApplication() : Application() {

    var moviesSearchPresenter : MoviesSearchViewModel? = null
}