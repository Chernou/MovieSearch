package com.example.moviesearch.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesearch.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val posterUrl = intent.getStringExtra("poster") ?: ""
        val movieId = intent.getStringExtra("id") ?: ""
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val viewPager = findViewById<ViewPager2>(R.id.details_view_pager)
        tabMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "ПОСТЕР"
                1 -> tab.text = "О ФИЛЬМЕ"
            }
        }
        viewPager.adapter =
            DetailsViewPagerAdapter(supportFragmentManager, lifecycle, posterUrl, movieId)
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}