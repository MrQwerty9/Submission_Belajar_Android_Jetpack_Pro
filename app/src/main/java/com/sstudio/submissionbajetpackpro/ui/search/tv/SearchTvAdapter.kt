package com.sstudio.submissionbajetpackpro.ui.search.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import kotlinx.android.synthetic.main.item_movie_search.view.*

class SearchTvAdapter : RecyclerView.Adapter<SearchTvAdapter.ViewHolder>() {

    var onItemClick: ((Tv) -> Unit)? = null
    var tv: List<Tv> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_search, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tv.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tv[position])
    }

    fun getSwipedData(swipedPosition: Int): Tv? = tv[swipedPosition]

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tv: Tv) {
            with(itemView) {
                txt_title.text = tv.originalName
                tv_overview.text = tv.overview
                tv_rating_item.text = tv.voteAverage.toString()
                tv_release_date.text = tv.firstAirDate
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + tv.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                setOnClickListener {
                    onItemClick?.invoke(tv)
                }
            }
        }
    }

}