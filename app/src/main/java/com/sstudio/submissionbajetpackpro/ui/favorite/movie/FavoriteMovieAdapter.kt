package com.sstudio.submissionbajetpackpro.ui.favorite.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteMovieAdapter(val adapterCallback: AdapterCallback) : PagedListAdapter<MovieFavorite, FavoriteMovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieFavorite>() {
            override fun areItemsTheSame(oldItem: MovieFavorite, newItem: MovieFavorite): Boolean {
                return oldItem.movie.id == newItem.movie.id
            }

            override fun areContentsTheSame(oldItem: MovieFavorite, newItem: MovieFavorite): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(favorite: MovieFavorite) {
            with(itemView) {
                txt_title.text = favorite.movie.originalTitle
                tv_overview.text = favorite.movie.overview
                tv_rating_item.text = favorite.movie.voteAverage.toString()
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + favorite.movie.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                itemView.setOnClickListener {
                    adapterCallback.itemMovieOnclick(favorite)
                }
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieFavorite? = getItem(swipedPosition)

    interface AdapterCallback{
        fun itemMovieOnclick(favorite: MovieFavorite)
    }
}