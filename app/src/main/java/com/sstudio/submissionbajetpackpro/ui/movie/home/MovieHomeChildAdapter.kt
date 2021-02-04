package com.sstudio.submissionbajetpackpro.ui.movie.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieHome
import kotlinx.android.synthetic.main.item_movie_child_home.view.*

class MovieHomeChildAdapter : RecyclerView.Adapter<MovieHomeChildAdapter.MovieViewHolder>() {

    private var movies = MovieHome()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setMovie(movies: MovieHome) {
        this.movies
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_child_home, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.listMovie.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(movies.listMovie[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(movie: Movie) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + movie.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_loading)
                            .error(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_error)
                    )
                    .into(img_poster)
                setOnClickListener {
                    onItemClick?.invoke(movie)
                }
            }
        }
    }
}