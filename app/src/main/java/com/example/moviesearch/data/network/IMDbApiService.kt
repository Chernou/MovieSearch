package com.example.moviesearch.data.network

import com.example.moviesearch.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {

    //@GET("/en/API/SearchMovie/k_iplzom1i/{expression}")
    //fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/SearchMovie/k_1ap5tjd2/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

}




