package com.sstudio.submissionbajetpackpro.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.movie_wrapper.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_MOVIE_TV = "extra_movie_tv"
        const val IS_MOVIE = "is_movie"
        const val IS_TV = "is_tv"
    }
    private var isFavorite: Boolean? = null
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val extras = intent.extras
        if (extras != null) {
            val movieTvId = extras.getInt(EXTRA_DETAIL, 0)
            val movieOrTv = extras.getString(EXTRA_MOVIE_TV)
            if (movieTvId != 0) {
                viewModel.setSelectedMovieTv(movieTvId)
                if (movieOrTv == IS_MOVIE) {
                    viewModel.getFavoriteMovieStatus(movieTvId).observe(this, {
                        isFavorite = it.isNotEmpty()

                    })
                    favoriteOnChange()
                    viewModel.detailMovie.observe(this, { resource ->
                        when (resource) {
                            is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                            is Resource.Success -> {
                                progress_bar.visibility = View.GONE
                                resource.data?.let { populateMovie(it) }
                            }
                            is Resource.Error -> {
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
                    viewModel.getFavoriteTvStatus(movieTvId).observe(this, {
                        isFavorite = it.isNotEmpty()
                        favoriteOnChange()
                    })
                    viewModel.detailTv.observe(this, { tvShow ->
                        when (tvShow) {
                            is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                            is Resource.Success -> {
                                progress_bar.visibility = View.GONE
                                tvShow.data?.let { populateTv(it) }
                            }
                            is Resource.Error -> {
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
                btn_favorite.setOnClickListener {
                    isFavorite?.let { isFavorite->
                        viewModel.updateFavorite(isFavorite, movieOrTv, movieTvId)
                        favoriteOnChange()
                    }
                }
            }
        }
    }

    private fun favoriteOnChange() {
        isFavorite?.let { isFavorite ->
            if (isFavorite) {
                btn_favorite.setImageResource(R.drawable.ic_favorite_pink)
            } else {
                btn_favorite.setImageResource(R.drawable.ic_favorite_border_white)
            }
        }
    }

    private fun populateMovie(movie: Movie) {
        tv_title.text = movie.originalTitle
        tv_overview.text = movie.overview
        tv_release_date.text = movie.releaseDate
        Glide.with(this)
            .load(BuildConfig.POSTER + movie.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_backdrop)
        Glide.with(this)
            .load(BuildConfig.POSTER + movie.posterPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster)
    }

    private fun populateTv(tvShow: Tv) {
        tv_title.text = tvShow.originalName
        tv_overview.text = tvShow.overview
        tv_release_date.text = tvShow.firstAirDate
        Glide.with(this)
            .load(BuildConfig.POSTER + tvShow.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_backdrop)
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
