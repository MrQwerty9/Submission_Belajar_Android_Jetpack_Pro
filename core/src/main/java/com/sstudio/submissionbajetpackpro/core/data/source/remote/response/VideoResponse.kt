package com.sstudio.submissionbajetpackpro.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("results")
    var video: List<Video>
){
    data class Video(
        var id: String = "",
        var key: String = "",
        var name: String = ""
    )
}