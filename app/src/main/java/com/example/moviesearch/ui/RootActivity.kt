package com.example.moviesearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviesearch.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.moviesSearchFragment,
                R.id.namesFragment,
                R.id.versionFragment -> bottomNavigationView.visibility = View.VISIBLE
                else -> bottomNavigationView.visibility = View.GONE
            }

        }
    }
}