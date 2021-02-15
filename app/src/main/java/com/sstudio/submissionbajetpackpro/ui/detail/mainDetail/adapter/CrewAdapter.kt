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

class CrewAdapter : RecyclerView.Adapter<CrewAdapter.GenreViewHolder>() {

    private var Crew = listOf<Credits.Crew>()
    var onItemClick: ((Credits.Crew) -> Unit)? = null

    fun setCrew(list: List<Credits.Crew>) {
        this.Crew
        this.Crew = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = Crew.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(Crew[position])
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(crew: Credits.Crew) {
            with(itemView) {
                tv_cast_name.text = crew.name
                tv_cast_character.text = crew.job
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + crew.profilePath)
                    .apply(
                        RequestOptions.placeholderOf(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_loading)
                            .error(com.sstudio.submissionbajetpackpro.core.R.drawable.ic_error)
                    )
                    .into(iv_cast)
                setOnClickListener {
                    onItemClick?.invoke(crew)
                }
            }
        }
    }
}