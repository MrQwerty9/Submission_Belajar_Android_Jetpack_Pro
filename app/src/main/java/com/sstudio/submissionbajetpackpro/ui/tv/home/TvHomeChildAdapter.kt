package com.sstudio.submissionbajetpackpro.ui.tv.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.model.TvHome
import kotlinx.android.synthetic.main.item_movie_poster.view.*

class TvHomeChildAdapter : RecyclerView.Adapter<TvHomeChildAdapter.MovieViewHolder>() {

    private var tvHome = TvHome()
    var onItemTvClick: ((Tv) -> Unit)? = null

    fun setMovie(movies: TvHome) {
        this.tvHome
        this.tvHome = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_poster, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = tvHome.listTv.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(tvHome.listTv[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(tv: Tv) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + tv.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_loading)
                            .error(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_error)
                    )
                    .into(img_poster)
                setOnClickListener {
                    onItemTvClick?.invoke(tv)
                }
            }
        }
    }
}