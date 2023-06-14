package com.example.moviesearch.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.moviesearch.data.LocalStorage
import com.example.moviesearch.data.NetworkClient
import com.example.moviesearch.data.converters.MovieCastConverter
import com.example.moviesearch.data.network.ImdbApiService
import com.example.moviesearch.data.network.RetrofitNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.ResourceProvider
import util.ResourceProviderImpl

val dataModule = module {

    single<ImdbApiService> {
        Retrofit.Builder()
            .baseUrl(RetrofitNetworkClient.IMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ImdbApiService::class.java)
    }

    single {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single {
        LocalStorage(get())
    }

    single {
        androidContext()
            .getSharedPreferences("FAVORITES_KEY", Context.MODE_PRIVATE)
    }

    single<ResourceProvider> {
        ResourceProviderImpl(androidContext())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    factory {
        MovieCastConverter()
    }
}