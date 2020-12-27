package com.sstudio.submissionbajetpackpro.ui.movie

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
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(val adapterCallback: AdapterCallback) : PagedListAdapter<MovieEntity, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
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
        fun bind(movie: MovieEntity) {
            with(itemView) {
                txt_title.text = movie.originalTitle
                tv_overview.text = movie.overview
                tv_rating_item.text = movie.voteAverage.toString()
                tv_release_date.text = movie.releaseDate
//                ContextCompat.getDrawable(itemView.context, movie.poster)
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + movie.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                itemView.setOnClickListener {
                    adapterCallback.itemMovieOnclick(movie)
                }
            }
        }
    }

    interface AdapterCallback{
        fun itemMovieOnclick(movie: MovieEntity)
    }
}