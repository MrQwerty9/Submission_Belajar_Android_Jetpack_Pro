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
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import kotlinx.android.synthetic.main.item_movie.view.*

class TvAdapter(val adapterCallback: AdapterCallback) : PagedListAdapter<TvEntity, TvAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tv: TvEntity) {
            with(itemView) {
                txt_title.text = tv.originalName
                tv_overview.text = tv.overview
                tv_rating_item.text = tv.voteAverage.toString()
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + tv.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                setOnClickListener {
                    adapterCallback.itemTvOnclick(tv)
                }
            }
        }
    }
    interface AdapterCallback{
        fun itemTvOnclick(tv: TvEntity)
    }
}