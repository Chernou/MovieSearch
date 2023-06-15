package com.example.moviesearch.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesearch.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsFragment : Fragment() {

    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        val posterUrl = requireArguments().getString(MOVIE_POSTER) ?: ""
        val movieId = requireArguments().getString(MOVIE_ID) ?: ""

        val viewPager = view.findViewById<ViewPager2>(R.id.details_view_pager)
        tabMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "ПОСТЕР" //todo move to resources
                1 -> tab.text = "О ФИЛЬМЕ"
            }
        }
        viewPager.adapter =
            DetailsViewPagerAdapter(childFragmentManager, lifecycle, posterUrl, movieId)
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }

    companion object {
        private const val MOVIE_POSTER = "MOVIE_POSTER"
        private const val MOVIE_ID = "MOVIE_ID"

        fun newInstance(posterUrl: String, movieId: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_POSTER, posterUrl)
                    putString(MOVIE_ID, movieId)
                }
            }
    }
}