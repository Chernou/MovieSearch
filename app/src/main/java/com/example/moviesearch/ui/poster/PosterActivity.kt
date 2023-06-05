package com.example.moviesearch.ui.poster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import util.Creator
import com.example.moviesearch.R
import com.example.moviesearch.presentation.poster.PosterPresenter
import com.example.moviesearch.presentation.poster.PosterView

class PosterActivity : Activity(), PosterView {

    private lateinit var presenter: PosterPresenter
    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.extras?.getString("poster", "")
        presenter = Creator.providePosterPresenter(this, url!!)
        setContentView(R.layout.activity_poster)
        poster = findViewById(R.id.poster)
        presenter.onCreate()
    }

    override fun setPosterImage(url: String) {
        Glide.with(poster)
            .load(url)
            .into(poster)
    }
}