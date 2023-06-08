package com.example.moviesearch.data.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.moviesearch.data.NetworkClient
import com.example.moviesearch.data.dto.MovieDetailsRequest
import com.example.moviesearch.data.dto.MoviesSearchRequest
import com.example.moviesearch.data.dto.Response
import org.koin.core.component.KoinComponent

class RetrofitNetworkClient(private val imdbService: ImdbApiService) : NetworkClient,
    KoinComponent {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            Log.d("!@#", "Not connected") //todo delete
            return Response().apply { resultCode = -1 }
        }
        return when (dto) {
            is MoviesSearchRequest -> {
                val resp = imdbService.searchMovies(dto.searchQuery).execute()
                val body = resp.body() ?: Response()
                Log.d("!@#", resp.code().toString()) //todo delete
                body.apply { resultCode = resp.code() }
            }
            is MovieDetailsRequest -> {
                val resp = imdbService.getMovieDetails(dto.movieId).execute()
                val body = resp.body() ?: Response()
                Log.d("!@#", resp.code().toString()) //todo delete
                body.apply { resultCode = resp.code() }
            }
            else -> {
                Log.d("!@#", "Successful") //todo delete
                Response().apply { resultCode = 400 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager: ConnectivityManager = getKoin().get()
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

    companion object {
        const val IMDB_BASE_URL = "https://imdb-api.com"
    }
}