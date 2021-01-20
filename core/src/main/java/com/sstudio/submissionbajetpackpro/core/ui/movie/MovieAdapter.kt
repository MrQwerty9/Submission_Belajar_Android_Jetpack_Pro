package com.sstudio.submissionbajetpackpro.core.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.R
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : PagedListAdapter<Movie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Movie) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
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

    fun getSwipedData(swipedPosition: Int): Movie? = getItem(swipedPosition)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            with(itemView) {
                txt_title.text = movie.originalTitle
                tv_overview.text = movie.overview
                tv_rating_item.text = movie.voteAverage.toString()
                tv_release_date.text = movie.releaseDate
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + movie.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                setOnClickListener {
                    onItemClick?.invoke(movie)
                }
            }
        }
    }
}