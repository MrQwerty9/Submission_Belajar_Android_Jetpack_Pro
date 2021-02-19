package com.sstudio.submissionbajetpackpro.ui.detail.mainDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.utils.DataTemp
import kotlinx.android.synthetic.main.item_movie_poster.view.*

class SimilarTvAdapter : RecyclerView.Adapter<SimilarTvAdapter.GenreViewHolder>() {

    private var genres = listOf<Tv>()
    var onItemClick: ((Tv) -> Unit)? = null

    fun setSimilar(list: List<Tv>?) {
        this.genres
        list?.let { this.genres = it}
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_poster, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(genres[position])
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(tv: Tv) {
            with(itemView) {
                tv_title.text = tv.originalName
                tv_rating.text = tv.voteAverage.toString()
                tv_genre.text = DataTemp.listTvGenre.find {
                    it.id == tv.genreIds.first()
                }?.name
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + tv.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_loading)
                            .error(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_error)
                    )
                    .into(img_poster)
                setOnClickListener {
                    onItemClick?.invoke(tv)
                }
            }
        }
    }
}