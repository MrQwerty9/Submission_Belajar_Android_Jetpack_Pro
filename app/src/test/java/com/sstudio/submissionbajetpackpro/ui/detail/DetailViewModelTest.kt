package com.sstudio.submissionbajetpackpro.ui.detail

import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val dummyTv = DataDummy.generateDummyTv()[0]
    private val movieId = dummyMovie.id
    private val tvId = dummyTv.id

    @Before
    fun test(){
        viewModel = DetailViewModel()
    }

    @Test
    fun testGetMovie() {
        viewModel.setSelectedMovieTv(movieId)
        val movieEntity = viewModel.getMovie()
        Assert.assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.title, movieEntity.originalTitle)
        assertEquals(dummyMovie.overview, movieEntity.overview)
        assertEquals(dummyMovie.poster, movieEntity.posterPath)

    }

    @Test
    fun testGetTv() {
        viewModel.setSelectedMovieTv(tvId)
        val tvEntity = viewModel.getTv()
        Assert.assertNotNull(tvEntity)
        assertEquals(dummyTv.id, tvEntity.id)
        assertEquals(dummyTv.title, tvEntity.originalTitle)
        assertEquals(dummyTv.overview, tvEntity.overview)
        assertEquals(dummyTv.poster, tvEntity.posterPath)
    }
}