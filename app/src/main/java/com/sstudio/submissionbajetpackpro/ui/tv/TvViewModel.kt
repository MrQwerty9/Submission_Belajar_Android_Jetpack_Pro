package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvEntity
import com.sstudio.submissionbajetpackpro.utils.DataDummy

class TvViewModel : ViewModel() {
    fun getTv(): List<MovieTvEntity> = DataDummy.generateDummyTv()
}