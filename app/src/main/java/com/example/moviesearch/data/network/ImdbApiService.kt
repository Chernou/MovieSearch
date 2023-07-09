package com.example.moviesearch.data.network

import com.example.moviesearch.data.dto.MovieCastResponse
import com.example.moviesearch.data.dto.MovieDetailsResponse
import com.example.moviesearch.data.dto.MoviesSearchResponse
import com.example.moviesearch.data.dto.NamesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApiService {
    @GET("/en/API/SearchMovie/k_iplzom1i/{expression}")
    fun searchMovies(@Path("expression") searchQuery: String): Call<MoviesSearchResponse>

    @GET("/en/API/SearchName/k_iplzom1i/{expression}")
    fun searchNames(@Path("expression") searchQuery: String): Call<NamesResponse>

    @GET("/en/API/Title/k_iplzom1i/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>

    @GET("/en/API/FullCast/k_iplzom1i/{movie_id}")
    fun getMovieCast(@Path("movie_id") movieId: String): Call<MovieCastResponse>

    //@GET("/en/API/SearchMovie/k_1ap5tjd2/{expression}")
    //fun searchMovies(@Path("expression") expression:String): Call<MoviesSearchResponse>
}




