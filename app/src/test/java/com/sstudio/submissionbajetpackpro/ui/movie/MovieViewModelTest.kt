package com.sstudio.submissionbajetpackpro.ui.movie

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel()
    }

    @Test
    fun testGetMovies() {
        val movieEntity = viewModel.getMovies()
        assertNotNull(movieEntity)
        assertEquals(10, movieEntity.size)
    }
}