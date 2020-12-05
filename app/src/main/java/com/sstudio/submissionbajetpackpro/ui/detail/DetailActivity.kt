package com.sstudio.submissionbajetpackpro.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.MovieTvEntity
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

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val extras = intent.extras
        if (extras != null) {
            val movieTvId = extras.getInt(EXTRA_DETAIL, 0)
            val movieOrTv = extras.getString(EXTRA_MOVIE_TV)
            if (movieTvId != 0) {
                viewModel.setSelectedMovieTv(movieTvId)
                if (movieOrTv == IS_MOVIE) {
                    populateMovieTv(viewModel.getMovie())
                }else{
                    populateMovieTv(viewModel.getTv())
                }
            }
        }
    }

    private fun populateMovieTv(movieTv: MovieTvEntity) {
        txt_title.text = movieTv.title
        txt_overview.text = movieTv.overview
        movieTv.poster.let {
            Glide.with(this)
                .load(it)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(img_poster)
        }
    }
}
