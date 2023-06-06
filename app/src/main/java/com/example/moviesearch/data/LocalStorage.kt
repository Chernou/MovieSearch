package com.example.moviesearch.data

import android.content.SharedPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocalStorage : KoinComponent{

    private val sharedPreferences: SharedPreferences by inject()

    fun addToFavorites(movieId: String) {
        changeFavorites(movieId = movieId, remove = false)
    }

    fun removeFromFavorites(movieId: String) {
        changeFavorites(movieId = movieId, remove = true)
    }

    fun getSavedFavorites(): Set<String> = sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()

    private fun changeFavorites(movieId: String, remove: Boolean) {
        val mutableSet = getSavedFavorites().toMutableSet()
        val modified = if (remove) mutableSet.remove(movieId) else mutableSet.add(movieId)
        if (modified) sharedPreferences.edit().putStringSet(FAVORITES_KEY, mutableSet).apply()
    }

    private companion object {
        const val FAVORITES_KEY = "FAVORITES_KEY"
    }
}