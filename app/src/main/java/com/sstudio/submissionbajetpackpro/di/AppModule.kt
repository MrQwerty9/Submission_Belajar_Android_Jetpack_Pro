package com.sstudio.submissionbajetpackpro.di

import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

import com.sstudio.submissionbajetpackpro.ui.detail.DetailViewModel
import com.sstudio.submissionbajetpackpro.ui.movie.home.MovieHomeViewModel
import com.sstudio.submissionbajetpackpro.ui.movie.list.MovieListViewModel
import com.sstudio.submissionbajetpackpro.ui.search.movie.SearchMovieViewModel
import com.sstudio.submissionbajetpackpro.ui.search.tv.SearchTvViewModel
import com.sstudio.submissionbajetpackpro.ui.tv.home.TvHomeViewModel
import com.sstudio.submissionbajetpackpro.ui.tv.list.TvListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieTvUseCase> { MovieTvInteractor(get())}
}

val viewModelModule = module {
    viewModel { MovieHomeViewModel(get()) }
    viewModel { MovieListViewModel(get()) }
    viewModel { TvHomeViewModel(get()) }
    viewModel { TvListViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchMovieViewModel(get()) }
    viewModel { SearchTvViewModel(get()) }
}