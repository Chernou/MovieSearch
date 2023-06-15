package com.example.moviesearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesearch.R
import com.example.moviesearch.core.navigation.NavigatorHolder
import com.example.moviesearch.core.navigation.NavigatorImpl
import com.example.moviesearch.ui.movies.MoviesSearchFragment
import org.koin.android.ext.android.inject

class RootActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator = NavigatorImpl(
        fragmentContainerViewId = R.id.fragment_container,
        fragmentManager = supportFragmentManager
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        if (savedInstanceState == null) {
            navigator.openFragment(
                MoviesSearchFragment.newInstance()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.attachNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.detachNavigator()
    }
}