package com.example.moviesearch.ui.movies

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.domain.MoviesState
import com.example.moviesearch.domain.models.Movie
import com.example.moviesearch.ui.details.DetailsFragment
import com.example.moviesearch.view_model.movies.MoviesSearchViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MoviesSearchFragment : Fragment() {

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textWatcher: TextWatcher


    private val adapter = MoviesAdapter(object : MoviesAdapter.MovieClickListener {
        override fun onMovieClick(movie: Movie) {
            if (clickDebounce()) {
                findNavController().navigate(
                    R.id.action_moviesSearchFragment_to_detailsFragment,
                    DetailsFragment.createArgs(movie.id, movie.image)
                )
            }
        }

        override fun onFavoriteToggleClick(movie: Movie) {
            viewModel.toggleFavorite(movie)
        }
    })

    private var isClickAllowed = true
    private val handler: Handler by inject()
    private val viewModel: MoviesSearchViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeholderMessage = view.findViewById(R.id.placeholderMessage)
        queryInput = view.findViewById(R.id.queryInput)
        moviesList = view.findViewById(R.id.locations)
        progressBar = view.findViewById(R.id.movies_progress_bar)

        viewModel.observeState().observe(this) {
            render(it)
        }

        viewModel.observeShowToast().observe(this) {
            showToast(it)
        }

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

        moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        textWatcher.let { queryInput.addTextChangedListener(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textWatcher.let { queryInput.removeTextChangedListener(it) }
    }

    private fun showToast(additionalMessage: String) {
        Toast.makeText(requireContext(), additionalMessage, Toast.LENGTH_LONG)
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

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}