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
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.viewmodel.ViewModelFactory
import com.sstudio.submissionbajetpackpro.vo.Status
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
                viewModel.getFavoriteStatus(movieTvId).observe(this, {
                        isFavorite = it.isNotEmpty()
                    favoriteOnChange()
                })
                if (movieOrTv == IS_MOVIE) {
                    viewModel.needFetch = false
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
                btn_favorite.setOnClickListener {
                    isFavorite?.let { isFavorite->
                        if (isFavorite) {
                            viewModel.deleteFavorite(movieTvId)
                        }
                        else{
                            viewModel.setFavorite(movieTvId)
                        }
                        favoriteOnChange()
                    }
                }
            }
        }
    }

    private fun favoriteOnChange() {
        isFavorite?.let { isFavorite ->
            if (isFavorite) {
                btn_favorite.setImageResource(R.drawable.ic_favorite_border_white)
            } else {
                btn_favorite.setImageResource(R.drawable.ic_favorite_pink)
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
