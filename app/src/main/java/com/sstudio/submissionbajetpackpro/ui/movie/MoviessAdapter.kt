package com.sstudio.submissionbajetpackpro.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import kotlinx.android.synthetic.main.list_photos.view.*


class MoviessAdapter : PagedListAdapter<MovieResponse.Result, MoviessAdapter.MovieViewHolder?>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieResponse.Result> = object : DiffUtil.ItemCallback<MovieResponse.Result>() {
            override fun areItemsTheSame(movie: MovieResponse.Result, t1: MovieResponse.Result): Boolean {
                return movie.id == t1.id
            }

            override fun areContentsTheSame(Movie: MovieResponse.Result, t1: MovieResponse.Result): Boolean {
                return true
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(R.layout.list_photos, viewGroup, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movieViewHolder.bind(it) }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: MovieResponse.Result) {
//        val url = GlideUrl(
//            getItem(i)?.url, LazyHeaders.Builder()
//                .addHeader("User-Agent", "your-user-agent")
//                .build()
//        )
            with(itemView) {
                Glide.with(context)
                    .load(BuildConfig.POSTER_THUMBNAIL + movie.posterPath)
                    .into(imageView)
            }
        }
    }
}