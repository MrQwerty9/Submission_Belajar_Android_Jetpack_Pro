package com.sstudio.submissionbajetpackpro.ui.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import kotlinx.android.synthetic.main.item_movie.view.*

class TvAdapter(val adapterCallback: AdapterCallback) : RecyclerView.Adapter<TvAdapter.ViewHolder>() {

    var tv: ArrayList<TvEntity> = ArrayList()

    fun setTv(movies: List<TvEntity>){
        this.tv.clear()
        this.tv.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tv.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = tv[position]
        holder.bind(tv)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tv: TvEntity) {
            with(itemView) {
                txt_title.text = tv.originalName
                txt_overview.text = tv.overview
//                ContextCompat.getDrawable(itemView.context, movie.poster)
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + tv.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                itemView.setOnClickListener {
                    adapterCallback.itemTvOnclick(tv)
                }
            }
        }
    }
    interface AdapterCallback{
        fun itemTvOnclick(tv: TvEntity)
    }
}