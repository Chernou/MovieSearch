package com.example.moviesearch.data

import com.example.moviesearch.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}