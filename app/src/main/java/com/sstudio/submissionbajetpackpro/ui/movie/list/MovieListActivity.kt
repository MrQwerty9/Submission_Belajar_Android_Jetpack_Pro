package com.sstudio.submissionbajetpackpro.ui.movie.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.ui.movie.MovieAdapter
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListActivity : AppCompatActivity() {

    companion object {
        val PARAMS_EXTRA = "paramsExtra"
    }

    private val viewModel: MovieListViewModel by viewModel()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val params = intent.getParcelableExtra<Params.MovieParams>(PARAMS_EXTRA)
        if (params == null){
            Log.d("mytag", "movieList null")
        }
        viewModel.getMovie(params)
        observeData()
        swipe_layout.setOnRefreshListener {
            viewModel.getMovie(params)
            observeData()
            swipe_layout.isRefreshing = false
        }

        movieAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
            startActivity(intent)
        }
        with(rv_list_movie) {
            layoutManager = LinearLayoutManager(context)
//                setHasFixedSize(true)
            adapter = movieAdapter
        }

        viewModel.state.observe(this, {
            when (it.status) {
                NetworkState.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                    Log.d("mytag", "state loading")
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
                    Log.d("mytag", "state success")
                }
                NetworkState.Status.EMPTY -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, "Tidak ada data ${it.msg}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, "Terjadi Kesalahan ${it.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeData() {
        movieAdapter = MovieAdapter()
        viewModel.listMovie?.observe(this) {
            movieAdapter.submitList(it)
        }
    }
}