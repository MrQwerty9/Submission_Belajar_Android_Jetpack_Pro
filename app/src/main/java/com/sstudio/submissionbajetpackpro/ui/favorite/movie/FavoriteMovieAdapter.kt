package com.sstudio.submissionbajetpackpro.ui.favorite.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteMovieAdapter(val adapterCallback: AdapterCallback) : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {

    var movies: ArrayList<MovieFavorite> = ArrayList()

    fun setMovies(movies: List<MovieFavorite>){
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(favorite: MovieFavorite) {
            with(itemView) {
                txt_title.text = favorite.movie.originalTitle
                tv_overview.text = favorite.movie.overview
//                ContextCompat.getDrawable(itemView.context, movie.poster)
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

    interface AdapterCallback{
        fun itemMovieOnclick(favorite: MovieFavorite)
    }
}