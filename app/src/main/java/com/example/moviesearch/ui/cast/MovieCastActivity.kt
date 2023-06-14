package com.example.moviesearch.ui.cast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.domain.CastState
import com.example.moviesearch.ui.details.AboutFragment
import com.example.moviesearch.view_model.cast.MovieCastViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieCastActivity : AppCompatActivity() {

    private val viewModel: MovieCastViewModel by viewModel {
        parametersOf(movieId)
    }
    private lateinit var movieId: String
    private lateinit var titleTextView: TextView
    private val adapter = ListDelegationAdapter(
        movieCastHeaderDelegate(),
        movieCastPersonDelegate(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_cast)

        movieId = intent.getStringExtra(AboutFragment.MOVIE_ID) ?: ""
        titleTextView = findViewById(R.id.title)

        val recyclerView = findViewById<RecyclerView>(R.id.movies_cast_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.observeCast().observe(this) {
            renderState(it)
        }
    }

    private fun renderState(castState: CastState) {
        when (castState) {
            is CastState.ContentState -> showContent(castState)
            is CastState.ErrorState -> showError(castState.errorMessage)
        }
    }

    private fun showContent(castState: CastState.ContentState) {
        titleTextView.text = castState.fullTitle
        adapter.items = castState.items
        adapter.notifyDataSetChanged()
    }

    private fun showError(errorMessage: String) {

    }
}