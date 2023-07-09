package com.example.moviesearch.data.dto

data class NamesResponse(
    val errorMessage: String,
    val expression: String,
    val results: List<ResultResponse>,
    val searchType: String
) : Response()

data class ResultResponse(
    val description: String,
    val id: String,
    val image: String,
    val resultType: String,
    val title: String
)