package com.sstudio.submissionbajetpackpro.ui.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.ui.favorite.movie.FavoriteMovieFragment
import com.sstudio.submissionbajetpackpro.ui.favorite.tv.FavoriteTvShowFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
       const val PAGE_COUNT = 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FavoriteMovieFragment()
            1 -> fragment = FavoriteTvShowFragment()
        }

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.resources.getString(R.string.tab_1)
            1 -> return context.resources.getString(R.string.tab_2)
        }

        return null
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }
}