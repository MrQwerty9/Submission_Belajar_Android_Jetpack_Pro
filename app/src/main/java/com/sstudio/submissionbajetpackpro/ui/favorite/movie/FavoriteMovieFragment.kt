package com.sstudio.submissionbajetpackpro.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

class FavoriteMovieFragment : Fragment(), FavoriteMovieAdapter.AdapterCallback {

    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            favoriteMovieAdapter = FavoriteMovieAdapter(this)

            progress_bar.visibility = View.VISIBLE
            viewModel.listMovie?.observe(this, { listMovie ->
                listMovie?.let { favoriteMovieAdapter.setMovies(it) }
                progress_bar.visibility = View.GONE
            })

            with(rv_list_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteMovieAdapter
            }
        }
    }

    override fun itemMovieOnclick(favorite: MovieFavorite) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, favorite.movie.id)
        startActivity(intent)
    }
}