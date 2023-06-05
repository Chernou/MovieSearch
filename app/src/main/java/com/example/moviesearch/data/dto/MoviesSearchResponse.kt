package com.example.moviesearch.data.dto

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: ArrayList<MovieDto>
) : Response()