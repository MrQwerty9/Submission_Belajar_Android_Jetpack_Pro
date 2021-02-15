package com.sstudio.submissionbajetpackpro.ui.detail.mainDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.detail.DetailData
import com.sstudio.submissionbajetpackpro.ui.detail.DetailViewModel
import com.sstudio.submissionbajetpackpro.ui.detail.mainDetail.adapter.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.fragment_main_detail.*
import kotlinx.android.synthetic.main.movie_wrapper.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainDetailFragment : Fragment() {

    private var isFavorite: Boolean? = null
    private val viewModel: DetailViewModel by viewModel()
    private val genreAdapter = GenreAdapter()
    private val similarMovieAdapter = SimilarMovieAdapter()
    private val similarTvAdapter = SimilarTvAdapter()
    private val castAdapter = CastAdapter()
    private val crewAdapter = CrewAdapter()
    private val videoAdapter = VideoAdapter()
    private lateinit var navController: NavController
    private var detailData: DetailData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.setSupportActionBar(appbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        navController = Navigation.findNavController(view)
        detailData = activity?.intent?.extras?.getParcelable(DetailActivity.EXTRA_DETAIL)
        if (detailData != null) {
            observer(detailData as DetailData)
            btn_favorite.setOnClickListener {
                isFavorite?.let { isFavorite ->
                    viewModel.updateFavorite(isFavorite)
                    favoriteOnChange()
                }
            }
        }
    }

    private fun observer(bundle: DetailData) {
        viewModel.setSelectedMovieTv(bundle.id, bundle.type)
        viewModel.getFavoriteStatus().observe(viewLifecycleOwner, {
            isFavorite = it
            favoriteOnChange()
        })
        if (bundle.type == DetailData.Type.MOVIE) {
            viewModel.detailMovie?.observe(viewLifecycleOwner, { resource ->
                when (resource) {
                    is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        resource.data?.let { populateMovie(it) }
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
            viewModel.getSimilarMovie?.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        similarMovieAdapter.setMovie(it.data)
                        rv_similar.adapter = similarMovieAdapter
                        populateSimilar()
                    }
                }
            }
        } else {
            viewModel.detailTv?.observe(viewLifecycleOwner, { tvShow ->
                when (tvShow) {
                    is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        tvShow.data?.let { populateTv(it) }
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
            viewModel.getSimilarTv?.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        rv_similar.adapter = similarTvAdapter
                        populateSimilar()
                        similarTvAdapter.setSimilar(it.data)
                    }
                }
            }
        }
        viewModel.getCredits?.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        pupulateCredits(it)
                    }
                }
            }
        }

        viewModel.getVideo?.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        populateVideo(it)
                    }
                }
            }
        }
    }

    private fun populateSimilar() {
        with(rv_similar) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        similarMovieAdapter.onItemClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(
                DetailActivity.EXTRA_DETAIL,
                DetailData(it.id, it.originalTitle, DetailData.Type.MOVIE)
            )
            startActivity(intent)
        }
        similarTvAdapter.onItemClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(
                DetailActivity.EXTRA_DETAIL,
                DetailData(it.id, it.originalName, DetailData.Type.TV_SHOW)
            )
            startActivity(intent)
        }
        btn_similar_show_more.setOnClickListener {
            navController.navigate(R.id.action_goto_similar)
        }
    }

    private fun populateVideo(video: List<Video>) {
        videoAdapter.setVideo(video)
        with(rv_trailer) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = videoAdapter
        }
        videoAdapter.onItemClick = {
            Log.d("mytag", "detail Fragm $it")
            val action = MainDetailFragmentDirections.actionGotoYoutubePlayer(it.key)
            navController.navigate(action)
        }
    }

    private fun pupulateCredits(it: Credits) {
        castAdapter.setCast(it.cast)
        crewAdapter.setCrew(it.crew)
        with(rv_cast) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = castAdapter
        }
    }

    private fun favoriteOnChange() {
        isFavorite?.let { isFavorite ->
            if (isFavorite) {
                btn_favorite.setImageResource(R.drawable.ic_favorite_border_favorite_24dp)
            } else {
                btn_favorite.setImageResource(R.drawable.ic_favorite_border_white)
            }
        }
    }

    private fun populateMovie(movieDetail: MovieDetail) {
        setToolbarTitle(movieDetail.movie.originalTitle)
        tv_overview.text = movieDetail.movie.overview
        tv_release_date.text = movieDetail.movie.releaseDate
        tv_rating.text = movieDetail.movie.voteAverage.toString() + " /"
        tv_votes.text = movieDetail.voteCount.toString()
        populateGenre(movieDetail.genres)
        Glide.with(this)
            .load(BuildConfig.POSTER + movieDetail.movie.posterPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(img_poster_detail)
        Glide.with(this)
            .load(BuildConfig.POSTER + movieDetail.movie.backdropPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(img_backdrop)
    }

    private fun populateTv(tvDetail: TvDetail) {
        setToolbarTitle(tvDetail.tv.originalName)
        tv_overview.text = tvDetail.tv.overview
        tv_release_date.text = tvDetail.tv.firstAirDate
        tv_rating.text = tvDetail.tv.voteAverage.toString() + " /"
        tv_votes.text = tvDetail.voteCount.toString()
        populateGenre(tvDetail.genres)
        Glide.with(this)
            .load(BuildConfig.POSTER + tvDetail.tv.backdropPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(img_backdrop)
        Glide.with(this)
            .load(BuildConfig.POSTER + tvDetail.tv.posterPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(img_poster_detail)
    }

    private fun populateGenre(genres: List<Genre>) {
        genreAdapter.setGenre(genres)
        with(rv_genre) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = genreAdapter
        }
    }

    private fun setToolbarTitle(title: String) {
        tv_title.text = title
        appbarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = title
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }
}