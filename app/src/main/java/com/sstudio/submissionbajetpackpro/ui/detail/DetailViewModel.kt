package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvEntity
import com.sstudio.submissionbajetpackpro.utils.DataDummy

class DetailViewModel : ViewModel() {
    private var movieTvId: Int = 0

    fun setSelectedMovieTv(courseId: Int) {
        this.movieTvId = courseId
    }

    fun getMovie(): MovieTvEntity {
        lateinit var movie: MovieTvEntity
        val moviesEntities = DataDummy.generateDummyMovies()
        for (movieEntity in moviesEntities) {
            if (movieEntity.id == movieTvId) {
                movie = movieEntity
            }
        }
        return movie
    }

    fun getTv(): MovieTvEntity {
        lateinit var tv: MovieTvEntity
        val tvEntities = DataDummy.generateDummyTv()
        for (tvEntity in tvEntities) {
            if (tvEntity.id == movieTvId) {
                tv = tvEntity
            }
        }
        return tv
    }
}