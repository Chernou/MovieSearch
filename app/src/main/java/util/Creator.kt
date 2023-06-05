package util

import android.content.Context
import com.example.moviesearch.data.LocalStorage
import com.example.moviesearch.domain.impl.MoviesRepositoryImpl
import com.example.moviesearch.data.network.RetrofitNetworkClient
import com.example.moviesearch.domain.api.MoviesInteractor
import com.example.moviesearch.domain.api.MoviesRepository
import com.example.moviesearch.domain.impl.MoviesInteractorImpl
import com.example.moviesearch.presentation.poster.PosterPresenter
import com.example.moviesearch.presentation.poster.PosterView

object Creator {

    fun provideMoviesInteractor(context: Context) : MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context),
            LocalStorage(context.getSharedPreferences("local_storage", Context.MODE_PRIVATE))
        )
    }

    fun providePosterPresenter(view: PosterView, url: String): PosterPresenter {
        return PosterPresenter(view, url)
    }
}