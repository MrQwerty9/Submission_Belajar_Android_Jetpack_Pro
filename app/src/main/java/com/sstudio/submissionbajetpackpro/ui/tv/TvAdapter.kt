package com.sstudio.submissionbajetpackpro.ui.tv

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
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import kotlinx.android.synthetic.main.item_movie.view.*

class TvAdapter : PagedListAdapter<Tv, TvAdapter.ViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Tv) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = getItem(position)
        tv?.let { holder.bind(it) }
    }

    fun getSwipedData(swipedPosition: Int): Tv? = getItem(swipedPosition)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tv: Tv) {
            with(itemView) {
                txt_title.text = tv.originalName
                tv_overview.text = tv.overview
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