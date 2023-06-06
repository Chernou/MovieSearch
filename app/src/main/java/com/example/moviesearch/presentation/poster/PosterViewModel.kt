package com.example.moviesearch.presentation.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PosterViewModel (private val url: String) : ViewModel() {

    private val posterUrl = MutableLiveData<String>()
    fun getPosterUrl(): LiveData<String> = posterUrl

}