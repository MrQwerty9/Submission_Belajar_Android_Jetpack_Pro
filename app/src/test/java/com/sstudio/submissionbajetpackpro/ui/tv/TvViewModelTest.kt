package com.sstudio.submissionbajetpackpro.ui.tv

import junit.framework.Assert
import org.junit.Before
import org.junit.Test


class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @Before
    fun setUp() {
        viewModel = TvViewModel()
    }

    @Test
    fun testGetMovies() {
        val tvEntity = viewModel.getTv()
        Assert.assertNotNull(tvEntity)
        Assert.assertEquals(10, tvEntity.size)
    }
}