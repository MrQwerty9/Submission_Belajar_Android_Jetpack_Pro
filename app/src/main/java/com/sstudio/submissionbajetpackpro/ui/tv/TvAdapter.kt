package com.sstudio.submissionbajetpackpro.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.MovieTvEntity
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_movie.view.*

class TvAdapter : RecyclerView.Adapter<TvAdapter.ViewHolder>() {

    var tv: ArrayList<MovieTvEntity> = ArrayList()

    fun setTv(movies: List<MovieTvEntity>){
        this.tv.clear()
        this.tv.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tv.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = tv[position]
        holder.bind(movie)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: MovieTvEntity) {
            with(itemView) {
                txt_title.text = movie.title
                txt_overview.text = movie.overview
                ContextCompat.getDrawable(itemView.context, movie.poster)
                Glide.with(itemView.context)
                    .load(movie.poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIL, movie.id)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_TV)
                    context.startActivity(intent)
                }
            }
        }
    }

}