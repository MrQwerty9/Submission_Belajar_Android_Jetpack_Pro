package com.sstudio.submissionbajetpackpro.ui.detail.mainDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.domain.model.Video
import kotlinx.android.synthetic.main.item_video.view.*

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.GenreViewHolder>() {

    private var videos = listOf<Video>()
    var onItemClick: ((Video) -> Unit)? = null

    fun setVideo(list: List<Video>) {
        this.videos
        this.videos = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(videos[position])
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(video: Video) {
            with(itemView) {
                tv_video_name.text = video.name
                Glide.with(itemView.context)
                    .load(BuildConfig.YOUTUBE_THUMBNAIL + video.key + "/0.jpg")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(iv_video_thumbnail)
                setOnClickListener {
                    onItemClick?.invoke(video)
                }
            }
        }
    }
}