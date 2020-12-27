package com.sstudio.submissionbajetpackpro.ui.favorite.tv

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
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvFavorite
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteTvShowAdapter(val adapterCallback: AdapterCallback) : PagedListAdapter<TvFavorite, FavoriteTvShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvFavorite>() {
            override fun areItemsTheSame(oldItem: TvFavorite, newItem: TvFavorite): Boolean {
                return oldItem.tv.id == newItem.tv.id
            }

            override fun areContentsTheSame(oldItem: TvFavorite, newItem: TvFavorite): Boolean {
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
        fun bind(favorite: TvFavorite) {
            with(itemView) {
                txt_title.text = favorite.tv.originalName
                tv_overview.text = favorite.tv.overview
//                ContextCompat.getDrawable(itemView.context, movie.poster)
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + favorite.tv.posterPath)
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
        fun itemMovieOnclick(favorite: TvFavorite)
    }
}