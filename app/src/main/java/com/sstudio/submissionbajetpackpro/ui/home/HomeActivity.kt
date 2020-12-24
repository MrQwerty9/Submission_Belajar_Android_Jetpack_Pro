package com.sstudio.submissionbajetpackpro.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.ui.favorite.FavoriteFragment
import com.sstudio.submissionbajetpackpro.ui.movie.MovieFragment
import com.sstudio.submissionbajetpackpro.ui.tv.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = this.getString(R.string.app_name)
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null){
            bottom_nav.selectedItemId = R.id.navigation_movie
        }
    }

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragment: Fragment
                when (item.itemId){
                    R.id.navigation_movie -> {
                        fragment = MovieFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.layout_container, fragment, fragment::class.java.simpleName
                            ).commit()
                        return true
                    }
                    R.id.navigation_tv -> {
                        fragment = TvShowFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.layout_container, fragment, fragment::class.java.simpleName
                            ).commit()
                        return true
                    }
                    R.id.navigation_favorite -> {
                        fragment = FavoriteFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.layout_container, fragment, fragment::class.java.simpleName
                            ).commit()
                        return true
                    }
                }
                return false
            }
        }
}