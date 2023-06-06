package com.example.moviesearch.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviesearch.R

class PosterActivity : AppCompatActivity() {

    private lateinit var posterImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        val url = intent.extras?.getString("poster", "")
        posterImageView = findViewById(R.id.poster)
        if (url != null) setPosterImage(url)
    }
//        val viewModel: PosterViewModel = getKoin().get {
//            parametersOf(url)
//        }
//        viewModel.getPosterUrl().observe(this) { posterUrl ->
//            setPosterImage(posterUrl)
//        }

    private fun setPosterImage(url: String) {
        Glide.with(posterImageView)
            .load(url)
            .into(posterImageView)
    }
}