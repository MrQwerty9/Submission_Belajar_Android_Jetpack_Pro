package com.sstudio.submissionbajetpackpro.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.viewmodel.ViewModelFactory
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
                    if (viewModel.detailMovie == null) {
                        viewModel.getMovie()
                    }
                    populateMovie(viewModel.detailMovie)
                } else {
                    if (viewModel.detailTv == null) {
                        viewModel.getTv()
                    }
                    populateTv(viewModel.detailTv)
                }
            }
        }
    }

    private fun populateTv(detailTv: LiveData<TvEntity>?) {
        detailTv?.observe(this, { tvShow ->
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
        })
    }

    private fun populateMovie(detailMovie: LiveData<MovieEntity>?) {
        detailMovie?.observe(this, { movie ->
            txt_title.text = movie.originalTitle
            txt_overview.text = movie.overview
            Glide.with(this)
                .load(BuildConfig.POSTER + movie.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(img_poster)
            progress_bar.visibility = View.GONE
        })
    }
}
