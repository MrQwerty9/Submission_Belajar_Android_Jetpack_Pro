package com.sstudio.submissionbajetpackpro.ui.detail.mainDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.domain.model.Credits
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter : RecyclerView.Adapter<CastAdapter.GenreViewHolder>() {

    private var genres = listOf<Credits.Cast>()
    var onItemClick: ((Credits.Cast) -> Unit)? = null

    fun setCast(list: List<Credits.Cast>) {
        this.genres
        this.genres = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(genres[position])
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(cast: Credits.Cast) {
            with(itemView) {
                tv_cast_name.text = cast.name
                tv_cast_character.text = cast.character
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + cast.profilePath)
                    .apply(
                        RequestOptions.placeholderOf(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_loading)
                            .error(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_error)
                    )
                    .into(iv_cast)
                setOnClickListener {
                    onItemClick?.invoke(cast)
                }
            }
        }
    }
}