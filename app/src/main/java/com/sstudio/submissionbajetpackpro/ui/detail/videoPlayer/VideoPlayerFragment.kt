package com.sstudio.submissionbajetpackpro.ui.detail.videoPlayer

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.sstudio.submissionbajetpackpro.R
import kotlinx.android.synthetic.main.fragment_video_player.*


class VideoPlayerFragment : Fragment() {

    private var initializedYouTubePlayer: YouTubePlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        return inflater.inflate(R.layout.fragment_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoId = VideoPlayerFragmentArgs.fromBundle(requireArguments()).videoId
        lifecycle.addObserver(youtube_player_view)
        youtube_player_view.enterFullScreen()
        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
                initializedYouTubePlayer = youTubePlayer
            }
        })
    }

    // pause when fragment goes offscreen
    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(visible)
        if (!visible && initializedYouTubePlayer != null)
            initializedYouTubePlayer!!.pause()
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

//    override fun onDestroyView() {
//        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        super.onDestroyView()
//    }
}