package com.sstudio.submissionbajetpackpro.ui.detail.mainDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.utils.DataTemp
import kotlinx.android.synthetic.main.item_movie_poster.view.*

class SimilarMovieAdapter : RecyclerView.Adapter<SimilarMovieAdapter.GenreViewHolder>() {

    private var movies = listOf<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setMovie(list: List<Movie>?) {
        this.movies
        list?.let { this.movies = it}
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_poster, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(movies[position])
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(movie: Movie) {
            with(itemView) {
                tv_title.text = movie.originalTitle
                tv_rating.text = movie.voteAverage.toString()
                tv_genre.text = DataTemp.listMovieGenre.find {
                    it.id == movie.genreIds.first()
                }?.name
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