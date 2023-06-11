package com.example.moviesearch.ui.cast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.moviesearch.R
import com.example.moviesearch.data.dto.MovieCastResponse
import com.example.moviesearch.domain.CastState
import com.example.moviesearch.ui.details.AboutFragment
import com.example.moviesearch.view_model.cast.MovieCastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieCastActivity : AppCompatActivity() {

    private val viewModel: MovieCastViewModel by viewModel {
        parametersOf(movieId)
    }
    //private lateinit var binding: ActivityMovieCastBinding
    private lateinit var movieId: String
    private lateinit var directorsTextView: TextView
    private lateinit var writersTextView: TextView
    private lateinit var actorsTextView: TextView
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_cast)

        movieId = intent.getStringExtra(AboutFragment.MOVIE_ID) ?: ""
        //binding = ActivityMovieCastBinding.inflate(layoutInflater)
        directorsTextView = findViewById(R.id.directors)
        writersTextView = findViewById(R.id.screenwriters)
        actorsTextView = findViewById(R.id.actors)
        titleTextView = findViewById(R.id.title)

        viewModel.observeCast().observe(this) {
            renderState(it)
        }
    }

    private fun renderState(castState: CastState) {
        when (castState) {
            is CastState.ContentState -> setViews(castState.castState)
            is CastState.ErrorState -> showError(castState.errorMessage)
        }
    }

    private fun setViews(castState: MovieCastResponse) {
        Log.d("!@#", castState.actors[1].toString())

        directorsTextView.text = castState.directors.items.joinToString("\n\n", "", "")
        writersTextView.text = castState.writers.items.joinToString("\n\n", "", "")
        actorsTextView.text = castState.actors.joinToString("\n\n", "", "")
        val title = "${castState.title} (${castState.year})"
        titleTextView.text = title
    }

    private fun showError(errorMessage: String) {

    }
}