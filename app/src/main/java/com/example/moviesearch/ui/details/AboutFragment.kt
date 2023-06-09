package com.example.moviesearch.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesearch.databinding.FragmentAboutBinding
import com.example.moviesearch.domain.AboutState
import com.example.moviesearch.domain.models.MovieDetails
import com.example.moviesearch.ui.cast.MovieCastActivity
import com.example.moviesearch.view_model.poster.AboutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    private val viewModel: AboutViewModel by viewModel {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeAboutState().observe(viewLifecycleOwner) {
            renderState(it)
        }

        binding.showCastButton.setOnClickListener {
            val intent = Intent(requireActivity(), MovieCastActivity::class.java)
            intent.putExtra(MOVIE_ID, requireArguments().getString(MOVIE_ID))
            startActivity(intent)
        }
    }

    private fun renderState(state: AboutState) {
        when (state) {
            is AboutState.ContentState -> showAboutViews(state.movieDetails)
            is AboutState.ErrorState -> showError(state.errorMessage)
        }
    }

    private fun showAboutViews(movieDetails: MovieDetails) {
        binding.error.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
        binding.title.text = movieDetails.title
        binding.movieRating.text = movieDetails.imDbRating
        binding.movieYear.text = movieDetails.year
        binding.movieCountry.text = movieDetails.countries
        binding.movieGenre.text = movieDetails.genres
        binding.movieDirector.text = movieDetails.directors
        binding.movieScreenwriter.text = movieDetails.writers
        binding.movieCasting.text = movieDetails.stars
        binding.moviePlot.text = movieDetails.plot
    }

    private fun showError(errorMessage: String) {
        binding.error.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.GONE
        binding.error.text = errorMessage
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        fun newInstance(movieId: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_ID, movieId)
                }
            }
    }
}