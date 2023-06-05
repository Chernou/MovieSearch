package com.example.moviesearch.presentation.poster

class PosterPresenter (private val view: PosterView, private val url: String) {
    fun onCreate() {
        view.setPosterImage(url)
    }
}