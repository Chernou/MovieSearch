package com.example.moviesearch.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.view_model.poster.PosterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterFragment : Fragment() {

    private val viewModel: PosterViewModel by viewModel {
        parametersOf(requireArguments().getString(POSTER_URL))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val posterImageView = view.findViewById<ImageView>(R.id.poster)
        setPosterImage(posterImageView, requireArguments().getString(POSTER_URL))
    }

    private fun setPosterImage(posterImageView: ImageView, url: String?) {
        Glide.with(posterImageView)
            .load(url)
            .into(posterImageView)
    }

    companion object {
        const val POSTER_URL = "POSTER_URL"

        fun newInstance(posterUrl: String) =
            PosterFragment().apply {
                arguments = Bundle().apply {
                    putString(POSTER_URL, posterUrl)
                }
            }
    }
}