package com.sstudio.submissionbajetpackpro.ui.detail.mainDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.domain.model.Genre
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var genres = listOf<Genre>()
    var onItemClick: ((Genre) -> Unit)? = null

    fun setGenre(genre: List<Genre>) {
        this.genres
        this.genres = genre
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(genres[position])
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(genre: Genre) {
            with(itemView) {
                genre_name.text = genre.name
                setOnClickListener {
                    onItemClick?.invoke(genre)
                }
            }
        }
    }
}