package com.sstudio.submissionbajetpackpro.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.viewmodel.ViewModelFactory
import com.sstudio.submissionbajetpackpro.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

class MovieFragment : Fragment(), MovieAdapter.AdapterCallback {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel
    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            movieAdapter = MovieAdapter(this)
            observeData()

            swipe_layout.setOnRefreshListener {
                viewModel.fetchListMovie()
                observeData()
                swipe_layout.isRefreshing = false
            }
            with(rv_list_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
//                adapter = movieAdapter
            }
        }
    }

    private fun observeData() {
        viewModel.listMovie?.observe(this, { listMovie ->
            if (listMovie != null) {
                when (listMovie.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        movieAdapter.submitList(listMovie.data)
                        rv_list_movie.adapter = movieAdapter //why??
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun itemMovieOnclick(movie: MovieEntity) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, movie.id)
        startActivity(intent)
    }
}
