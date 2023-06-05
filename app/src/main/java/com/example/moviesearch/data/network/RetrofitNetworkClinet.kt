package com.example.moviesearch.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.moviesearch.data.NetworkClient
import com.example.moviesearch.data.dto.MoviesSearchRequest
import com.example.moviesearch.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient(private val context: Context) : NetworkClient {

    private val imdbBaseUrl = "https://imdb-api.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApiService::class.java)

    override fun doRequest(dto: Any): Response {

        if (!isConnected()) {
            Log.d("!@#", "Not connected") //todo delete
            return Response().apply { resultCode = -1 }

        }
        return if (dto is MoviesSearchRequest) {
            val resp = imdbService.searchMovies(dto.expression).execute()
            val body = resp.body() ?: Response()
            Log.d("!@#", resp.code().toString()) //todo delete
            body.apply { resultCode = resp.code() }
        } else {
            Log.d("!@#", "Successful") //todo delete
            Response().apply { resultCode = 400 }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}