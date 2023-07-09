package com.example.moviesearch.data.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.moviesearch.data.NetworkClient
import com.example.moviesearch.data.dto.MovieCastRequest
import com.example.moviesearch.data.dto.MovieDetailsRequest
import com.example.moviesearch.data.dto.MoviesSearchRequest
import com.example.moviesearch.data.dto.NamesRequest
import com.example.moviesearch.data.dto.Response
import org.koin.core.component.KoinComponent

class RetrofitNetworkClient(private val imdbService: ImdbApiService) : NetworkClient,
    KoinComponent {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            Log.d("!@#", "Not connected") //todo delete
            return Response().apply { resultCode = -1 }
        }
        if ((dto !is MoviesSearchRequest)
            && (dto !is MovieDetailsRequest)
            && (dto !is MovieCastRequest)
            && (dto !is NamesRequest)) {
            return Response().apply { resultCode = 400 }
        }
        val response = when (dto) {
            is MoviesSearchRequest -> imdbService.searchMovies(dto.searchQuery).execute()
            is MovieDetailsRequest -> imdbService.getMovieDetails(dto.movieId).execute()
            is NamesRequest -> imdbService.searchNames(dto.searchQuery).execute()
            else -> imdbService.getMovieCast((dto as MovieCastRequest).movieId).execute()
        }
        val body = response.body()
        return body?.apply { resultCode = response.code() } ?: Response().apply {
            resultCode = response.code()
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