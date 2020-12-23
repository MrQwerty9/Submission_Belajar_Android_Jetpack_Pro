package com.sstudio.submissionbajetpackpro.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.viewmodel.ViewModelFactory
import com.sstudio.submissionbajetpackpro.vo.Status
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_MOVIE_TV = "extra_movie_tv"
        const val IS_MOVIE = "is_movie"
        const val IS_TV = "is_tv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val extras = intent.extras
        if (extras != null) {
            val movieTvId = extras.getInt(EXTRA_DETAIL, 0)
            val movieOrTv = extras.getString(EXTRA_MOVIE_TV)
            if (movieTvId != 0) {
                viewModel.setSelectedMovieTv(movieTvId)
                if (movieOrTv == IS_MOVIE) {
                    viewModel.detailMovie.observe(this, { movie ->
                        when (movie.status) {
                            Status.LOADING -> progress_bar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                progress_bar.visibility = View.GONE
                                movie.data?.let { populateMovie(it) }
                            }
                            Status.ERROR -> {
                                progress_bar.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    "Terjadi Kesalahan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                } else {
                    viewModel.detailTv.observe(this, { tvShow ->
                        when (tvShow.status) {
                            Status.LOADING -> progress_bar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                progress_bar.visibility = View.GONE
                                tvShow.data?.let { populateTv(it) }
                            }
                            Status.ERROR -> {
                                progress_bar.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    "Terjadi Kesalahan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        }
    }

    private fun populateMovie(movie: MovieEntity) {
        txt_title.text = movie.originalTitle
        txt_overview.text = movie.overview
        Glide.with(this)
            .load(BuildConfig.POSTER + movie.posterPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster)
    }

    private fun populateTv(tvShow: TvEntity) {
        txt_title.text = tvShow.originalName
        txt_overview.text = tvShow.overview
        Glide.with(this)
            .load(BuildConfig.POSTER + tvShow.posterPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster)
        progress_bar.visibility = View.GONE
    }
}
