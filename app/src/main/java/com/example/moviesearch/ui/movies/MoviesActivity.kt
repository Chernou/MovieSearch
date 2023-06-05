package com.example.moviesearch.ui.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.domain.MoviesState
import com.example.moviesearch.domain.models.Movie
import com.example.moviesearch.presentation.movies.MoviesSearchViewModel
import com.example.moviesearch.ui.poster.PosterActivity

class MoviesActivity : ComponentActivity() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private lateinit var viewModel: MoviesSearchViewModel

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textWatcher: TextWatcher

    private val adapter = MoviesAdapter(object : MoviesAdapter.MovieClickListener {
        override fun onMovieClick(movie: Movie) {
            if (clickDebounce()) {
                val intent = Intent(this@MoviesActivity, PosterActivity::class.java)
                intent.putExtra("poster", movie.image)
                startActivity(intent)
            }
        }
        override fun onFavoriteToggleClick(movie: Movie) {
            viewModel.toggleFavorite(movie)
        }
    })

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        viewModel = ViewModelProvider(
            this,
            MoviesSearchViewModel.getViewModelFactory()
        )[MoviesSearchViewModel::class.java]

        viewModel.observeState().observe(this) {
            render(it)
        }

        viewModel.observeShowToast().observe(this) {
            showToast(it)
        }

        placeholderMessage = findViewById(R.id.placeholderMessage)
        queryInput = findViewById(R.id.queryInput)
        moviesList = findViewById(R.id.locations)
        progressBar = findViewById(R.id.progressBar)



        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        textWatcher?.let { queryInput.addTextChangedListener(it) }
    }

    private fun showToast(additionalMessage: String) {
        Toast.makeText(this, additionalMessage, Toast.LENGTH_LONG)
            .show()
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)

        }
    }

    private fun showLoading() {
        moviesList.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        moviesList.visibility = View.GONE
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        placeholderMessage.text = errorMessage
    }

    private fun showContent(movies: List<Movie>) {
        moviesList.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        adapter.movies.clear()
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty(emptyMessage: String) {
        moviesList.visibility = View.GONE
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        placeholderMessage.text = emptyMessage
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
} 