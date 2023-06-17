package com.example.moviesearch.ui.cast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.domain.CastState
import com.example.moviesearch.view_model.cast.MovieCastViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.parameter.parametersOf

class MovieCastFragment : Fragment() {

    private val viewModel: MovieCastViewModel by activityViewModel {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }
    private lateinit var titleTextView: TextView
    private val adapter = ListDelegationAdapter(
        movieCastHeaderDelegate(),
        movieCastPersonDelegate(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeCast().observe(this) {
            renderState(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_cast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView = view.findViewById(R.id.title)
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_cast_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
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

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        fun createArgs(movieId: String): Bundle = bundleOf(MOVIE_ID to movieId)
    }
}