package com.sstudio.submissionbajetpackpro.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.movie.MovieAdapter
import com.sstudio.submissionbajetpackpro.ui.tv.TvAdapter
import kotlinx.android.synthetic.main.activity_search_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMovieActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvAdapter: TvAdapter
    private val searchViewModel: SearchViewModel by viewModel()
    private var movieOrTv: String? = null
    companion object{
        const val IS_MOVIE = "is_movie"
        const val IS_TV = "is_tv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        supportActionBar?.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            movieOrTv = extras.getString(DetailActivity.EXTRA_MOVIE_TV)
            if (movieOrTv == IS_MOVIE) {
                populateMovie()
            }else{
                pupulateTv()
            }
        }
    }

    private fun populateMovie() {
        movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
            startActivity(intent)
        }
        searchViewModel.listSearchMovie?.observe(this){
            movieAdapter.
        }
        with(rv_search_movie){
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@SearchMovieActivity)
        }
    }

    private fun pupulateTv() {
        tvAdapter = TvAdapter()
        tvAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
            startActivity(intent)
        }
        with(rv_search_movie){
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@SearchMovieActivity)
        }
    }
}